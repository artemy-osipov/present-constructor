package io.github.artemy.osipov.shop.testdata

import io.github.artemy.osipov.shop.controller.dto.DAddPresent
import io.github.artemy.osipov.shop.controller.dto.DPresentItem
import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

object PresentTestData {

    val PRESENT_ID: UUID = UUID.fromString("89a83839-5a67-41a4-9d8f-be2035b2f58a")

    fun present(): Present {
        return Present().apply {
            id = PRESENT_ID
            name = "name"
            price = BigDecimal("42.25")
            date = LocalDateTime.of(2022, 1, 10, 14, 25)
            items = listOf(
                    Present.Item().apply {
                        candyId = CANDY_ID
                        count = 5
                    }
            )
        }
    }

    object REST {
        fun addDTO(): DAddPresent {
            val present = present()
            return DAddPresent().apply {
                name = present.name
                price = present.price
                items = listOf(
                        DPresentItem().apply {
                            candyId = CANDY_ID
                            count = 5
                        }
                )
            }
        }
    }
}
