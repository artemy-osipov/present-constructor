package io.github.artemy.osipov.shop.service.candy

import java.util.UUID
import java.math.BigDecimal

data class CreateCandyCommand(
    val id: UUID,
    val name: String,
    val firm: String,
    val price: BigDecimal,
    val grams: BigDecimal?,
    val order: BigDecimal
)