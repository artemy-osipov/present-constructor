package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CandyRepository : MongoRepository<Candy, UUID> {

    companion object {
        fun CandyRepository.getById(id: UUID): Candy {
            return findById(id).orElseThrow {
                EntityNotFoundException(
                    Candy::class.java, id
                )
            }
        }
    }
}
