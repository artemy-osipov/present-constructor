package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.List

@Repository
class PresentRepository(
    private val entityTemplate: R2dbcEntityTemplate,
    private val transactionOperator: TransactionalOperator
) {

    suspend fun findById(id: UUID): Present {
        return entityTemplate.selectOne(
            Query.query(Criteria.where("id").`is`(id)),
            PresentDTO::class.java
        )
            .flatMapMany { aggregatePresent(listOf(it)) }
            .`as`(transactionOperator::transactional)
            .awaitFirstOrNull()
            ?: throw EntityNotFoundException(Present::class, id)
    }

    fun findAll(): Flow<Present> {
        return entityTemplate.select(
            Query.empty(),
            PresentDTO::class.java
        )
            .collectList()
            .flatMapMany() { aggregatePresent(it) }
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
        return entityTemplate.select(
            Query.query(Criteria.where("present_id").`in`(presentIds)),
            ItemDTO::class.java
        )
    }

    suspend fun add(present: Present): UUID = transactionOperator.executeAndAwait {
        val dto = PresentDTO(present)
        val added = entityTemplate.insert(dto).awaitSingle()
        addItems(added.id, present.items)
        added.id
    }!!

    private suspend fun addItems(presentId: UUID, items: List<Present.Item>) {
        Flux.fromIterable(
            items.map { ItemDTO(presentId, it) }
        )
            .concatMap(entityTemplate::insert)
            .awaitLast()
    }

    suspend fun deleteById(id: UUID): Int {
        return entityTemplate.delete(
            Query.query(Criteria.where("id").`is`(id)),
            PresentDTO::class.java
        ).awaitSingle()
    }

    suspend fun deleteAll(): Int {
        return entityTemplate
            .delete(PresentDTO::class.java)
            .all()
            .awaitSingle()
    }

    suspend fun count(): Long {
        return entityTemplate
            .count(Query.empty(), PresentDTO::class.java)
            .awaitSingle()
    }

    private fun buildPresent(presentDTO: PresentDTO, itemsDTO: List<ItemDTO>): Present {
        return Present(
            presentDTO.id,
            presentDTO.name,
            presentDTO.price,
            presentDTO.date,
            itemsDTO.map { Present.Item(it.candyId, it.count) }.toMutableList()
        )
    }
}

@Table("presents")
private data class PresentDTO(
    @Id
    val id: UUID,
    var name: String,
    var price: BigDecimal,
    var date: LocalDateTime
) {
    constructor(present: Present) : this(present.id, present.name, present.price, present.date)
}

@Table("present_items")
private data class ItemDTO(
    val presentId: UUID,
    val candyId: UUID,
    var count: Int
) {
    constructor(presentId: UUID, item: Present.Item) : this(presentId, item.candyId, item.count)
}
