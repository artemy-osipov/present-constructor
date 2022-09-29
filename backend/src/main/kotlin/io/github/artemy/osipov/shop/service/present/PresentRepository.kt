package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.repository.ExtraMethods
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.asFlux
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Repository
class PresentRepository(
    private val presentDTORepository: PresentDTORepository,
    private val presentItemDTORepository: PresentItemDTORepository,
    private val transactionOperator: TransactionalOperator
) {

    suspend fun findById(id: UUID): Present = transactionOperator.executeAndAwait {
        val present = presentDTORepository.findById(id)
            ?: throw EntityNotFoundException(Present::class, id)
        aggregatePresent(listOf(present)).awaitFirstOrNull()
    }!!

    fun findAll(): Flow<Present> {
        return presentDTORepository.findAll()
            .asFlux()
            .collectList()
            .flatMapMany { aggregatePresent(it) }
            .`as`(transactionOperator::transactional)
            .asFlow()
    }

    private fun aggregatePresent(presentsDTO: List<PresentDTO>): Flux<Present> {
        return findItems(presentsDTO)
            .collectList()
            .map { it.groupBy { i -> i.presentId } }
            .map { presentsDTO.map { p -> buildPresent(p, it[p.id] ?: listOf()) } }
            .flatMapMany { Flux.fromIterable(it) }
    }

    private fun findItems(presentsDTO: List<PresentDTO>): Flux<ItemDTO> {
        val presentIds = presentsDTO.map { it.id }.toSet()
        return presentItemDTORepository.findByPresentIdIn(presentIds).asFlux()
    }

    suspend fun add(present: Present): UUID = transactionOperator.executeAndAwait {
        val dto = PresentDTO(present)
        val added = presentDTORepository.add(dto)
        addItems(added.id, present.items)
        added.id
    }!!

    private suspend fun addItems(presentId: UUID, items: List<Present.Item>) {
        presentItemDTORepository.saveAll(
            items.map { ItemDTO(presentId, it) }
        ).collect()
    }

    suspend fun deleteById(id: UUID) {
        presentDTORepository.deleteById(id)
    }

    suspend fun deleteAll() {
        return presentDTORepository.deleteAll()
    }

    suspend fun count(): Long {
        return presentDTORepository.count()
    }

    private fun buildPresent(presentDTO: PresentDTO, itemsDTO: List<ItemDTO>): Present {
        return Present(
            presentDTO.id,
            presentDTO.name,
            presentDTO.price,
            presentDTO.createDate,
            itemsDTO.map { Present.Item(it.candyId, it.count) }.toMutableList()
        )
    }
}

interface PresentDTORepository : CoroutineCrudRepository<PresentDTO, UUID>, ExtraMethods<PresentDTO>

interface PresentItemDTORepository : CoroutineCrudRepository<ItemDTO, UUID>, ExtraMethods<ItemDTO> {
    fun findByPresentIdIn(ids: Collection<UUID>): Flow<ItemDTO>
}

@Table("presents")
data class PresentDTO(
    @Id
    val id: UUID,
    var name: String,
    var price: BigDecimal,
    var createDate: LocalDateTime
) {
    constructor(present: Present) : this(present.id, present.name, present.price, present.createDate)
}

@Table("present_items")
data class ItemDTO(
    val presentId: UUID,
    val candyId: UUID,
    var count: Int
) {
    constructor(presentId: UUID, item: Present.Item) : this(presentId, item.candyId, item.count)
}
