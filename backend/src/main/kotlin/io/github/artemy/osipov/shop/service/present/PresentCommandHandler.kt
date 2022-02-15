package io.github.artemy.osipov.shop.service.present

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class PresentCommandHandler(
    private val presentRepository: PresentRepository
) {
    fun on(event: CreatePresentCommand): Mono<Present> {
        val present = Present(
            id = event.id,
            name = event.name,
            price = event.price,
            date = LocalDateTime.now()
        )
        event.items.forEach { (candyId, count) ->
            present.addItem(candyId, count)
        }
        return presentRepository.save(present)
    }

    fun on(event: RemovePresentCommand): Mono<Void> {
        return presentRepository.deleteById(event.id)
    }
}