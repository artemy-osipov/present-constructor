package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.service.candy.Candy
import io.github.artemy.osipov.shop.service.present.Present

import java.math.BigDecimal
import java.util.Comparator
import java.util.UUID
import java.util.function.Function
import java.util.stream.Collectors

data class ReportPresent(
    val name: String,
    val price: BigDecimal,
    val items: List<Item>
) {
    constructor (present: Present, candies: Collection<Candy>) : this(
        name = present.name,
        price = present.price,
        items = buildItems(present, candies)
    )

    private companion object {
        fun buildItems(present: Present, candies: Collection<Candy>): List<Item> {
            val idToCandy: Map<UUID, Candy> = candies.stream()
                .collect(
                    Collectors.toMap(
                        Candy::id,
                        Function.identity()
                    )
                )
            return present.items
                .stream()
                .map { i -> Item(idToCandy[i.candyId]!!, i.count) }
                .sorted(Comparator.comparing { i -> i.candy.order })
                .collect(Collectors.toList())
        }
    }

    fun computeCost(): BigDecimal {
        return items.stream()
            .map { item -> item.candy.price.multiply(BigDecimal(item.count)) }
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    }

    data class Item(
        val candy: Candy,
        val count: Int
    )
}
