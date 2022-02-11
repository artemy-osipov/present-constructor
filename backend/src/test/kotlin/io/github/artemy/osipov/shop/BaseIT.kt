package io.github.artemy.osipov.shop

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer

@SpringBootTest
@AutoConfigureMockMvc
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
