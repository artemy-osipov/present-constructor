package io.github.artemy.osipov.shop.controller.dto

import java.math.BigDecimal
import javax.validation.constraints.Digits
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class DEditCandy(
    @field:NotNull
    @field:Size(min = 1, max = 50)
    var name: String?,

    @field:NotNull
    @field:Size(min = 1, max = 50)
    var firm: String?,

    @field:NotNull
    @field:Min(1)
    @field:Digits(integer = 6, fraction = 2)
    var price: BigDecimal?,

    @field:NotNull
    @field:Min(1)
    @field:Digits(integer = 6, fraction = 2)
    var grams: BigDecimal?,

    @field:NotNull
    @field:Min(1)
    @field:Digits(integer = 4, fraction = 2)
    var order: BigDecimal?
)