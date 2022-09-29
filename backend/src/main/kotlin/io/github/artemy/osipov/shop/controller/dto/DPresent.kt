package io.github.artemy.osipov.shop.controller.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class DPresent(
    var id: UUID,
    var name: String,
    var price: BigDecimal,
    var createDate: LocalDateTime,
    var items: List<DPresentItem>
)