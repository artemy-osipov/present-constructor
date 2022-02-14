package io.github.artemy.osipov.shop.service.present

import java.util.UUID
import java.math.BigDecimal

class CreatePresentCommand(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val items: List<PresentItem>
) {
    data class PresentItem(
        val candyId: UUID,
        val count: Int
    )
}

