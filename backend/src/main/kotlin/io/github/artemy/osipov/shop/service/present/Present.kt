package io.github.artemy.osipov.shop.service.present

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class Present(
    @Id
    val id: UUID,
    var name: String,
    var price: BigDecimal,
    var createDate: LocalDateTime,
    val items: MutableList<Item> = mutableListOf()
) {
    fun addItem(candyId: UUID, count: Int) {
        items.add(
            Item(candyId, count)
        )
    }

    @Table("present_items")
    data class Item(
        val candyId: UUID,
        var count: Int
    )
}
