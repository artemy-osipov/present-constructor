package io.github.artemy.osipov.shop.controller.dto

import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.Digits
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

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