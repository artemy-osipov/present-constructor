package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

class CandyQueryControllerIT : BaseIT() {

    @Autowired
    lateinit var webClient: WebTestClient

    companion object {
        @BeforeAll
        @JvmStatic
        fun init(@Autowired repository: CandyRepository) = runTest {
            repository.add(CandyTestData.candy())
        }

        @AfterAll
        @JvmStatic
        fun clean(@Autowired repository: CandyRepository) = runTest {
            repository.deleteAll()
        }
    }

    @Test
    fun `should get candy by id`() {
        val candy = CandyTestData.candy()

        webClient.get()
            .uri("/api/candies/{id}", candy.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json("""
                               {
                                 "id": "${candy.id}",
                                 "name": "${candy.name}",
                                 "firm": "${candy.firm}",
                                 "price": ${candy.price},
                                 "order": ${candy.order},
                                 "active": ${candy.active}
                               }""")
    }

    @Test
    fun `should fail return unknown candy`() {
        val unknownId = newUUID()

        webClient.get()
            .uri("/api/candies/{id}", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should list all candies`() {
        webClient.get()
            .uri("/api/candies")
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath("$", hasSize<String>(1))
    }
}