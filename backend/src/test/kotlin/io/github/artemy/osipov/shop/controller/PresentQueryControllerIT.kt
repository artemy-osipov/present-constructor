package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.format.DateTimeFormatter

class PresentQueryControllerIT : BaseIT() {

    @Autowired
    lateinit var webClient: WebTestClient

    companion object {
        @BeforeAll
        @JvmStatic
        fun init(
            @Autowired candyRepository: CandyRepository,
            @Autowired presentRepository: PresentRepository
        ) = runTest {
            candyRepository.add(CandyTestData.candy())
            presentRepository.add(PresentTestData.present())
        }

        @AfterAll
        @JvmStatic
        fun clean(
            @Autowired candyRepository: CandyRepository,
            @Autowired presentRepository: PresentRepository
        ) = runTest {
            presentRepository.deleteAll()
            candyRepository.deleteAll()
        }
    }

    @Test
    fun `should get present by id`() {
        val present = PresentTestData.present()

        webClient.get()
            .uri("/api/presents/{id}", PRESENT_ID)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(
                """
                                {
                                  "id": "${present.id}",
                                  "name": "${present.name}",
                                  "price": ${present.price},
                                  "date": "${present.date.format(DateTimeFormatter.ISO_DATE_TIME)}",
                                  "items": [{
                                      "candyId": "532d5f3b-25e6-4adc-b99a-da74cb5be876",
                                      "count": 5
                                  }]
                                }"""
            )
    }

    @Test
    fun `should fail return unknown present`() {
        val unknownId = newUUID()

        webClient.get()
            .uri("/api/presents/{id}", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should list presents`() {
        webClient.get()
            .uri("/api/presents")
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath("$", hasSize<Any>(1))
    }
}