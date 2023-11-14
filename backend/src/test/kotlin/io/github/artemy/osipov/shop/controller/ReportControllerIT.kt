package io.github.artemy.osipov.shop.controller

import fr.opensagres.xdocreport.core.io.XDocArchive
import fr.opensagres.xdocreport.document.docx.DocxUtils
import io.github.artemy.osipov.shop.BaseIT
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.testdata.CandyTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

class ReportControllerIT : BaseIT() {

    val contentDisposition =
        "form-data; name=\"attachment\"; filename=\"=?UTF-8?Q?name_42.25_RUB.docx?=\"; filename*=UTF-8''name%2042.25%20RUB.docx"

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
    fun `should generate public report`() {
        val report = webClient.get()
            .uri("/api/presents/{id}/public-report", PRESENT_ID)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-Disposition", contentDisposition)
            .returnResult<ByteArray>()
            .responseBody
            .blockFirst()!!.let {
                XDocArchive.readZip(it.inputStream())
            }

        assert(
            DocxUtils.isDocx(report)
        )
    }

    @Test
    fun `should fail generate public report by unknown present`() {
        val unknownId = newUUID()

        webClient.get()
            .uri("/api/presents/{id}/public-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `should generate private report`() {
        val report = webClient.get()
            .uri("/api/presents/{id}/private-report", PRESENT_ID)
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("Content-Disposition", contentDisposition)
            .returnResult<ByteArray>()
            .responseBody
            .blockFirst()!!.let {
                XDocArchive.readZip(it.inputStream())
            }

        assert(
            DocxUtils.isDocx(report)
        )
    }

    @Test
    fun `should fail generate private report by unknown present`() {
        val unknownId = newUUID()

        webClient.get()
            .uri("/api/presents/{id}/private-report", unknownId)
            .exchange()
            .expectStatus().isNotFound
    }
}