package io.github.artemy.osipov.shop

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer

@SpringBootTest
@AutoConfigureWebTestClient
abstract class BaseIT {

    companion object {
        @JvmStatic
        val mongoDBContainer = MongoDBContainer("mongo:4.4.12").also {
            it.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun initProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl)
        }
    }
}
