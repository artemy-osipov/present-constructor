package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.artemy.osipov.shop.service.report.Report
import io.github.artemy.osipov.shop.service.report.ReportService
import io.github.artemy.osipov.shop.testdata.ReportTestData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

import static io.github.artemy.osipov.shop.testdata.PresentTestData.PRESENT_ID
import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import static org.hamcrest.core.Is.is
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.doThrow
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ReportController)
class ReportControllerIT {

    @MockBean
    ReportService reportService

    @Autowired
    MockMvc mockMvc

    static final String CONTENT_DISPOSITION = "form-data; name=\"attachment\"; filename*=UTF-8''name%202.4%20RUB.docx"

    @Test
    void 'should generate public report'() {
        Report report = ReportTestData.report()
        doReturn(report)
                .when(reportService)
                .generatePublicReport(PRESENT_ID)

        mockMvc.perform(get("/api/presents/{id}/public-report", PRESENT_ID))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()))
    }

    @Test
    void 'should fail generate public report by unknown present'() {
        def unknownId = newUUID()
        doThrow(EntityNotFoundException)
                .when(reportService)
                .generatePublicReport(unknownId)

        mockMvc.perform(get("/api/presents/{id}/public-report", unknownId))
                .andExpect(status().isNotFound())
    }

    @Test
    void 'should generate private report'() {
        Report report = ReportTestData.report()
        doReturn(report)
                .when(reportService)
                .generatePrivateReport(PRESENT_ID)

        mockMvc.perform(get("/api/presents/{id}/private-report", PRESENT_ID))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()))
    }

    @Test
    void 'should fail generate private report by unknown present'() {
        def unknownId = newUUID()
        doThrow(EntityNotFoundException)
                .when(reportService)
                .generatePrivateReport(unknownId)

        mockMvc.perform(get("/api/presents/{id}/private-report", unknownId))
                .andExpect(status().isNotFound())
    }
}