package io.github.artemy.osipov.shop.controller.dto

import java.math.BigDecimal
import java.util.*

class DCandy(
    var id: UUID,
    var name: String,
    var firm: String,
    var price: BigDecimal,
    var order: Double
)