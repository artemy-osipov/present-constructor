package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.service.candy.CandyRepository.Companion.getById
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CandyCommandHandler(
    private val repository: CandyRepository
) {
    fun on(event: CreateCandyCommand): Mono<Candy> {
        val candy = Candy(
            id = event.id,
            name = event.name,
            firm = event.firm,
            price = event.price,
            order = event.order
        )
        return repository.save(candy)
    }

    fun on(event: UpdateCandyCommand): Mono<Candy> {
        return repository.getById(event.id)
            .map { candy ->
                candy.apply {
                    name = event.name
                    firm = event.firm
                    price = event.price
                    order = event.order
                }
            }
            .flatMap(repository::save)
    }

    fun on(event: HideCandyCommand): Mono<Candy> {
        return repository.getById(event.id)
            .doOnNext { it.hide() }
            .flatMap(repository::save)
    }
}