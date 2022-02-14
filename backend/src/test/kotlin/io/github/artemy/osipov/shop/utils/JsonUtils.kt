package io.github.artemy.osipov.shop.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val mapper = jacksonObjectMapper()

fun toJson(o: Any): String {
    return mapper.writeValueAsString(o)
}
