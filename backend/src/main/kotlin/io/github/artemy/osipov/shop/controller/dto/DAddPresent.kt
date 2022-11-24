package io.github.artemy.osipov.shop.controller.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class DAddPresent(
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var name: String?,

    @field:NotNull
    @field:Min(1)
    @field:Digits(integer = 5, fraction = 2)
    var price: BigDecimal?,

    @field:NotEmpty
    @field:Valid
    var items: Collection<DPresentItem>?
)