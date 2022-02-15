package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.present.Present
import io.github.artemy.osipov.shop.service.report.Report
import io.github.artemy.osipov.shop.service.report.ReportService
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.testdata.ReportTestData
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono

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
        doReturn(Mono.just(report))
            .`when`(reportService)
            .generatePublicReport(PRESENT_ID)

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
        doReturn(Mono.error<Report>(EntityNotFoundException(Present::class.java, unknownId)))
            .`when`(reportService)
            .generatePublicReport(unknownId)

        webClient.get()
            .uri("/api/presents/{id}/public-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should generate private report`() {
        val report = ReportTestData.report()
        doReturn(Mono.just(report))
            .`when`(reportService)
            .generatePrivateReport(PRESENT_ID)

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
        doReturn(Mono.error<Report>(EntityNotFoundException(Present::class.java, unknownId)))
            .`when`(reportService)
            .generatePrivateReport(unknownId)

        webClient.get()
            .uri("/api/presents/{id}/private-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }
}