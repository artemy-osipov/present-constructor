package io.github.artemy.osipov.shop

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseIT {

    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.12").tap {
        start()
    }

    @DynamicPropertySource
    static void initProperties(DynamicPropertyRegistry registry) {
        registry.add('spring.data.mongodb.uri') {
            mongoDBContainer.replicaSetUrl
        }
    }
}
