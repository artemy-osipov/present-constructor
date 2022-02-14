package io.github.artemy.osipov.shop.controller.dto

data class DError(
    var internalMessage: String,
    var details: Map<String, String> = mapOf()
)
