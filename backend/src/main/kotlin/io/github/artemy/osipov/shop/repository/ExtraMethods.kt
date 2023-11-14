package io.github.artemy.osipov.shop.repository

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

interface ExtraMethods<T> {
    suspend fun add(entity: T & Any): T
}

class ExtraMethodsImpl<T>(
    private val template: R2dbcEntityTemplate
) : ExtraMethods<T> {

    override suspend fun add(entity: T & Any): T {
        return template.insert(entity).awaitSingle()
    }

}