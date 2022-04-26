package io.github.artemy.osipov.shop.repository

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Mono

interface ExtraMethods<T> {
    fun add(entity: T): Mono<T>
}

class ExtraMethodsImpl<T>(
    private val template: R2dbcEntityTemplate
) : ExtraMethods<T> {

    override fun add(entity: T): Mono<T> {
        return template.insert(entity)
    }

}