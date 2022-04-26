package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.CandyTestData.CANDY_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import io.github.artemy.osipov.shop.utils.toJson
import org.hamcrest.Matchers.aMapWithSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class CandyControllerIT : BaseIT() {

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var candyRepository: CandyRepository

    @AfterEach
    fun clean() {
        candyRepository.deleteAll().block()
    }

    @Test
    fun `should add candy with valid data`() {
        val result = webClient.post()
            .uri("/api/candies")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(CandyTestData.REST.updateDTO()))
            .exchange()

        val addedCandy = candyRepository.findAll().blockFirst()
        result
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("\"${addedCandy?.id}\"")
    }

    @Test
    fun `should not add candy with invalid data`() {
        webClient.post()
            .uri("/api/candies")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{}")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.details", aMapWithSize<Any, Any>(4))
    }

    @Test
    fun `should edit candy with valid data`() {
        candyRepository.add(CandyTestData.candy()).block()
        val newName = "new name"
        val request = CandyTestData.REST.updateDTO().apply {
            name = newName
        }

        webClient.put()
            .uri("/api/candies/{id}", CANDY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(request))
            .exchange()
            .expectStatus().isNoContent

        val updatedCandy = candyRepository.findById(CANDY_ID).block()
        assert(
            updatedCandy == CandyTestData.candy().apply {
                name = newName
            }
        )
    }

    @Test
    fun `should not edit candy with invalid data`() {
        webClient.put()
            .uri("/api/candies/{id}", CANDY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{}")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.details", aMapWithSize<Any, Any>(4))
    }

    @Test
    fun `should not edit nonexistent candy`() {
        val unknownId = newUUID()

        webClient.put()
            .uri("/api/candies/{id}", unknownId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(CandyTestData.REST.updateDTO()))
            .exchange()
            .expectStatus().isNotFound

        assert(candyRepository.count().block() == 0L)
    }

    @Test
    fun `should remove candy`() {
        candyRepository.add(CandyTestData.candy()).block()

        webClient.delete()
            .uri("/api/candies/{id}", CANDY_ID)
            .exchange()
            .expectStatus().isNoContent

        val hiddenCandy = candyRepository.findById(CANDY_ID).block()
        assert(hiddenCandy?.active == false)
    }

    @Test
    fun `should not remove unknown candy`() {
        val unknownId = newUUID()

        webClient.delete()
            .uri("/api/candies/{id}", unknownId)
            .exchange()
            .expectStatus().isNotFound

        assert(candyRepository.count().block() == 0L)
    }
}
