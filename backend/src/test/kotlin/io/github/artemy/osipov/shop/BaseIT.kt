package io.github.artemy.osipov.shop

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.CockroachContainer

@SpringBootTest
@AutoConfigureWebTestClient
abstract class BaseIT {

    companion object {
        @JvmStatic
        val cockroachDBContainer = CockroachContainer("cockroachdb/cockroach:v21.2.8").also {
            it.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun initProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url") {
                "r2dbc:postgresql://${cockroachDBContainer.host}:${cockroachDBContainer.getMappedPort(26257)}/${cockroachDBContainer.databaseName}"
            }
            registry.add("spring.r2dbc.username") {
                cockroachDBContainer.username
            }
            registry.add("spring.r2dbc.password") {
                cockroachDBContainer.password
            }
        }
    }
}
