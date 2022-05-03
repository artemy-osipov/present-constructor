package io.github.artemy.osipov.shop.service.candy

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.repository.ExtraMethods
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface CandyRepository : CoroutineCrudRepository<Candy, UUID>, ExtraMethods<Candy> {

    companion object {
        suspend fun CandyRepository.getById(id: UUID): Candy {
            return findById(id)
                ?: throw EntityNotFoundException(Candy::class, id)
        }
    }
}
