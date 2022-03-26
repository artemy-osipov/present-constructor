package io.github.artemy.osipov.shop.service.candy

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import java.math.BigDecimal

@Document("candies")
data class Candy(
    @Id
    val id: UUID,
    var name: String,
    var firm: String,
    var price: BigDecimal,
    var order: Double,
    var active: Boolean = true
) {
    fun hide() {
        active = false
    }
}