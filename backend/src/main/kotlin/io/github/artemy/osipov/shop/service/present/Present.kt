package io.github.artemy.osipov.shop.service.present

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

@Document("presents")
data class Present(
    @Id
    val id: UUID,
    var name: String,
    var price: BigDecimal,
    var date: LocalDateTime,
    val items: MutableList<Item> = mutableListOf()
) {
    fun addItem(candyId: UUID, count: Int) {
        items.add(
            Item(candyId, count)
        )
    }

    data class Item(
        val candyId: UUID,
        var count: Int
    )
}
