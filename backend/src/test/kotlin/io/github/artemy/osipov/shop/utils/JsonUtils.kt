package io.github.artemy.osipov.shop.utils

import com.fasterxml.jackson.databind.ObjectMapper

private val mapper = ObjectMapper()

fun toJson(o: Any): String {
    return mapper.writeValueAsString(o)
}
