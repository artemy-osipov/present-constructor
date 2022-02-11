package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.report.ReportService
import io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import io.github.artemy.osipov.shop.testdata.ReportTestData
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ReportController::class)
class ReportControllerTest {

    val contentDisposition = "form-data; name=\"attachment\"; filename*=UTF-8''name%2042.25%20RUB.docx"

    @MockBean
    lateinit var reportService: ReportService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should generate public report`() {
        val report = ReportTestData.report()
        doReturn(report)
                .`when`(reportService)
                .generatePublicReport(PRESENT_ID)

        mockMvc.get("/api/presents/{id}/public-report", PRESENT_ID)
                .andExpect {
                    status { isOk() }
                    header { string("Content-Disposition", contentDisposition) }
                    content { bytes(report.content) }
                }
    }

    @Test
    fun `should fail generate public report by unknown present`() {
        val unknownId = newUUID()
        doThrow(EntityNotFoundException::class.java)
                .`when`(reportService)
                .generatePublicReport(unknownId)

        mockMvc.get("/api/presents/{id}/public-report", unknownId)
                .andExpect {
                    status { isNotFound() }
                }
    }

    @Test
    fun `should generate private report`() {
        val report = ReportTestData.report()
        doReturn(report)
                .`when`(reportService)
                .generatePrivateReport(PRESENT_ID)

        mockMvc.get("/api/presents/{id}/private-report", PRESENT_ID)
                .andExpect {
                    status { isOk() }
                    header { string("Content-Disposition", contentDisposition) }
                    content { bytes(report.content) }
                }
    }

    @Test
    fun `should fail generate private report by unknown present`() {
        val unknownId = newUUID()
        doThrow(EntityNotFoundException::class.java)
                .`when`(reportService)
                .generatePrivateReport(unknownId)

        mockMvc.get("/api/presents/{id}/private-report", unknownId)
                .andExpect {
                    status { isNotFound() }
                }
    }
}