package io.github.artemy.osipov.shop.controller.dto

import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class DPresentItem(
    @NotNull
    @Valid
    var candyId: UUID,

    @NotNull
    @Min(1)
    var count: Int
)