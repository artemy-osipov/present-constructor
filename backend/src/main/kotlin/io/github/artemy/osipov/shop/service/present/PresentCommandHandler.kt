package io.github.artemy.osipov.shop.service.present

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PresentCommandHandler(
    private val presentRepository: PresentRepository
) {
    fun on(event: CreatePresentCommand) {
        val present = Present(
            id = event.id,
            name = event.name,
            price = event.price,
            date = LocalDateTime.now()
        )
        event.items.forEach { (candyId, count) ->
            present.addItem(candyId, count)
        }
        presentRepository.save(present)
    }

    fun on(event: RemovePresentCommand) {
        presentRepository.deleteById(event.id)
    }
}