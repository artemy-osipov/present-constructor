package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.List

@Repository
class PresentRepository(
    private val entityTemplate: R2dbcEntityTemplate,
    private val transactionOperator: TransactionalOperator
) {

    fun findById(id: UUID): Mono<Present> {
        return entityTemplate.selectOne(
            Query.query(Criteria.where("id").`is`(id)),
            PresentDTO::class.java
        )
            .flatMapMany { aggregatePresent(listOf(it)) }
            .next()
            .switchIfEmpty(
                Mono.error(
                    EntityNotFoundException(
                        Present::class, id
                    )
                )
            )
            .`as`(transactionOperator::transactional)
    }

    fun findAll(): Flux<Present> {
        return entityTemplate.select(
            Query.empty(),
            PresentDTO::class.java
        )
            .collectList()
            .flatMapMany() { aggregatePresent(it) }
            .`as`(transactionOperator::transactional)
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

    fun add(present: Present): Mono<UUID> {
        val dto = PresentDTO(present)
        return entityTemplate.insert(dto)
            .flatMap { addItems(it.id, present.items) }
            .`as`(transactionOperator::transactional)
    }

    private fun addItems(presentId: UUID, items: List<Present.Item>): Mono<UUID> {
        val insertItems = items
            .map { ItemDTO(presentId, it) }
            .map { entityTemplate.insert(it) }
        return Mono.zip(insertItems) { presentId }
    }

    fun deleteById(id: UUID): Mono<Int> {
        return entityTemplate.delete(
            Query.query(Criteria.where("id").`is`(id)),
            PresentDTO::class.java
        )
    }

    fun deleteAll(): Mono<Int> {
        return entityTemplate.delete(PresentDTO::class.java).all()
    }

    fun count(): Mono<Long> {
        return entityTemplate.count(Query.empty(), PresentDTO::class.java)
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
