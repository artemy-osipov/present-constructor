package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface CandyRepository : ReactiveMongoRepository<Candy, UUID> {

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
