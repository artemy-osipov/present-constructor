package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import io.github.artemy.osipov.shop.utils.toJson
import org.hamcrest.collection.IsMapWithSize.aMapWithSize
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

class PresentControllerIT : BaseIT() {

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var presentRepository: PresentRepository

    @AfterEach
    fun clean() {
        presentRepository.deleteAll().block()
    }

    @Test
    fun `should add present with valid data`() {
        val result = webClient.post()
            .uri("/api/presents")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(PresentTestData.REST.addDTO()))
            .exchange()

        val addedPresent = presentRepository.findAll().blockFirst()
        result
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("\"${addedPresent?.id}\"")
    }

    @Test
    fun `should not add present with invalid data`() {
        webClient.post()
            .uri("/api/presents")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{}")
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.details", aMapWithSize<Any, Any>(3))
    }

    @Test
    fun `should remove present`() {
        presentRepository.save(PresentTestData.present()).block()

        webClient.delete()
            .uri("/api/presents/{id}", PRESENT_ID)
            .exchange()
            .expectStatus().isNoContent

        assert(presentRepository.count().block() == 0L)
    }

    @Test
    fun `should not remove unknown present`() {
        val unknownId = newUUID()

        webClient.delete()
            .uri("/api/presents/{id}", unknownId)
            .exchange()
            .expectStatus().isNoContent

        assert(presentRepository.count().block() == 0L)
    }
}