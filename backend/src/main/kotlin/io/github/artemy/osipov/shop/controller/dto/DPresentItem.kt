package io.github.artemy.osipov.shop.controller.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class DPresentItem(
    @NotNull
    @Valid
    var candyId: UUID,

    @NotNull
    @Min(1)
    var count: Int
)