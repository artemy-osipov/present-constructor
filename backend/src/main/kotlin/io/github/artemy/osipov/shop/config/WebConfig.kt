package io.github.artemy.osipov.shop.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.core.Ordered
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class WebConfig {
    @Bean
    fun corsFilter(): FilterRegistrationBean<CorsFilter> {
        val cors = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", cors)
        return FilterRegistrationBean(CorsFilter(source)).apply {
            order = Ordered.HIGHEST_PRECEDENCE
        }
    }
}