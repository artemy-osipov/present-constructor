package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.service.candy.CandyRepository.Companion.getById
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class CandyCommandHandler(
    private val repository: CandyRepository
) {
    suspend fun on(event: CreateCandyCommand) {
        val candy = Candy(
            id = event.id,
            name = event.name,
            firm = event.firm,
            price = event.price,
            order = event.order
        )
        repository.save(candy).awaitSingle()
    }

    suspend fun on(event: UpdateCandyCommand) {
        val candy = repository.getById(event.id).awaitSingle()
        candy.apply {
            name = event.name
            firm = event.firm
            price = event.price
            order = event.order
        }
        repository.save(candy).awaitSingle()
    }

    suspend fun on(event: HideCandyCommand) {
        val candy = repository.getById(event.id).awaitSingle()
        candy.hide()
        repository.save(candy).awaitSingle()
    }
}