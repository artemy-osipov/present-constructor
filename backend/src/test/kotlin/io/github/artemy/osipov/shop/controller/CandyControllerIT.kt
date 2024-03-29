package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.testdata.CandyTD
import io.github.artemy.osipov.shop.testdata.CandyTD.CANDY_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import io.github.artemy.osipov.shop.utils.toJson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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
    fun clean() = runTest {
        candyRepository.deleteAll()
    }

    @Test
    fun `should add candy with valid data`() = runTest {
        val result = webClient.post()
            .uri("/api/candies")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(CandyTD.REST.updateDTO()))
            .exchange()

        val addedCandy = candyRepository.findAll().first()
        result
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("\"${addedCandy.id}\"")
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
    fun `should edit candy with valid data`() = runTest {
        candyRepository.add(CandyTD.candy())
        val newName = "new name"
        val request = CandyTD.REST.updateDTO().apply {
            name = newName
        }

        webClient.put()
            .uri("/api/candies/{id}", CANDY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(request))
            .exchange()
            .expectStatus().isNoContent

        val updatedCandy = candyRepository.findById(CANDY_ID)
        assert(
            updatedCandy == CandyTD.candy().apply {
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
    fun `should not edit nonexistent candy`() = runTest {
        val unknownId = newUUID()

        webClient.put()
            .uri("/api/candies/{id}", unknownId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(CandyTD.REST.updateDTO()))
            .exchange()
            .expectStatus().isNotFound

        assert(candyRepository.count() == 0L)
    }

    @Test
    fun `should remove candy`() = runTest {
        candyRepository.add(CandyTD.candy())

        webClient.delete()
            .uri("/api/candies/{id}", CANDY_ID)
            .exchange()
            .expectStatus().isNoContent

        val hiddenCandy = candyRepository.findById(CANDY_ID)
        assert(hiddenCandy?.active == false)
    }

    @Test
    fun `should not remove unknown candy`() = runTest {
        val unknownId = newUUID()

        webClient.delete()
            .uri("/api/candies/{id}", unknownId)
            .exchange()
            .expectStatus().isNotFound

        assert(candyRepository.count() == 0L)
    }
}
