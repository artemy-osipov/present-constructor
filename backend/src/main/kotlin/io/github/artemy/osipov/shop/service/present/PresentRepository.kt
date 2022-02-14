package io.github.artemy.osipov.shop.service.present

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID
import io.github.artemy.osipov.shop.exception.EntityNotFoundException

interface PresentRepository : MongoRepository<Present, UUID> {

    companion object {
        fun PresentRepository.getById(id: UUID): Present {
            return findById(id).orElseThrow {
                EntityNotFoundException(
                    Present::class.java, id
                )
            }
        }
    }
}