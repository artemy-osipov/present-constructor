package io.github.artemy.osipov.shop.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy
import org.springframework.data.relational.core.mapping.NamingStrategy

@Configuration
class R2dbcConfig {

    @Bean
    fun r2dbcMappingContext(
        namingStrategy: ObjectProvider<NamingStrategy>,
        r2dbcCustomConversions: R2dbcCustomConversions
    ): R2dbcMappingContext {
        val r2dbcMappingContext = R2dbcMappingContext(
            namingStrategy.getIfAvailable { DefaultNamingStrategy.INSTANCE }
        )
        r2dbcMappingContext.setSimpleTypeHolder(r2dbcCustomConversions.simpleTypeHolder)
        r2dbcMappingContext.isForceQuote = true
        return r2dbcMappingContext
    }
}
