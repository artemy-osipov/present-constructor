package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.report.ReportService
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.testdata.ReportTestData
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.stub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(ReportController::class)
class ReportControllerTest {

    val contentDisposition = "form-data; name=\"attachment\"; filename*=UTF-8''name%2042.25%20RUB.docx"

    @MockBean
    lateinit var reportService: ReportService

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should generate public report`() {
        val report = ReportTestData.report()
        reportService.stub {
            onBlocking { generatePublicReport(PRESENT_ID) } doReturn report
        }

        webClient.get()
            .uri("/api/presents/{id}/public-report", PRESENT_ID)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-Disposition", contentDisposition)
            .expectBody<ByteArray>().isEqualTo(report.content)
    }

    @Test
    fun `should fail generate public report by unknown present`() {
        val unknownId = newUUID()
        reportService.stub {
            onBlocking { generatePublicReport(unknownId) } doThrow EntityNotFoundException::class
        }

        webClient.get()
            .uri("/api/presents/{id}/public-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should generate private report`() {
        val report = ReportTestData.report()
        reportService.stub {
            onBlocking { generatePrivateReport(PRESENT_ID) } doReturn report
        }

        webClient.get()
            .uri("/api/presents/{id}/private-report", PRESENT_ID)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-Disposition", contentDisposition)
            .expectBody<ByteArray>().isEqualTo(report.content)
    }

    @Test
    fun `should fail generate private report by unknown present`() {
        val unknownId = newUUID()
        reportService.stub {
            onBlocking { generatePrivateReport(unknownId) } doThrow EntityNotFoundException::class
        }

        webClient.get()
            .uri("/api/presents/{id}/private-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }
}