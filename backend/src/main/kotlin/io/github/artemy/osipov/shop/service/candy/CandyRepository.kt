package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.repository.ExtraMethods
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface CandyRepository : ReactiveCrudRepository<Candy, UUID>, ExtraMethods<Candy> {

    companion object {
        fun CandyRepository.getById(id: UUID): Mono<Candy> {
            return findById(id)
                .switchIfEmpty(
                    Mono.error(
                        EntityNotFoundException(
                            Candy::class, id
                        )
                    )
                )
        }
    }
}
