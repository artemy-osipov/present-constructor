package io.github.artemy.osipov.shop.service.present

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*

interface PresentRepository : ReactiveMongoRepository<Present, UUID> {

    companion object {
        fun PresentRepository.getById(id: UUID): Mono<Present> {
            return findById(id).switchIfEmpty(
                Mono.error(
                    EntityNotFoundException(
                        Present::class, id
                    )
                )
            )
        }
    }
}