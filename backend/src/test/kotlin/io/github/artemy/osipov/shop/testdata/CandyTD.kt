package io.github.artemy.osipov.shop.testdata

import io.github.artemy.osipov.shop.controller.dto.DEditCandy
import io.github.artemy.osipov.shop.service.candy.Candy
import java.math.BigDecimal
import java.util.*

object CandyTD {

    val CANDY_ID: UUID = UUID.fromString("532d5f3b-25e6-4adc-b99a-da74cb5be876")

    fun candy(): Candy {
        return Candy(
            id = CANDY_ID,
            name = "name",
            firm = "firm",
            price = BigDecimal("4.20"),
            grams = BigDecimal("12.50"),
            order = BigDecimal("51.00"),
            active = true
        )
    }

    object REST {
        fun updateDTO(): DEditCandy {
            val candy = candy()
            return DEditCandy(
                name = candy.name,
                firm = candy.firm,
                price = candy.price,
                grams = candy.grams,
                order = candy.order
            )
        }
    }
}
