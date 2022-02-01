package io.github.artemy.osipov.shop.controller


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import io.github.artemy.osipov.present.exception.EntityNotFoundException

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportController.class)
class ReportControllerIT {

    @MockBean
    private io.github.artemy.osipov.shop.service.report.ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    private static final String CONTENT_DISPOSITION = "form-data; name=\"attachment\"; filename*=UTF-8''name%202.4%20RUB.docx";

    private io.github.artemy.osipov.shop.service.report.Report getReport() {
        return new io.github.artemy.osipov.shop.service.report.Report("name 2.4 RUB.docx", new byte[]{1, 2, 3});
    }

    @Test
    void publicReportWithExistentIdShouldReturnReport() throws Exception {
        io.github.artemy.osipov.shop.service.report.Report report = getReport();

        when(reportService.generatePublicReport(any())).thenReturn(report);

        mockMvc.perform(get("/api/presents/{id}/public-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    void publicReportWithNonExistentIdShouldReturn404() throws Exception {
        when(reportService.generatePublicReport(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/presents/{id}/public-report", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void privateReportWithExistentIdShouldReturnReport() throws Exception {
        io.github.artemy.osipov.shop.service.report.Report report = getReport();

        when(reportService.generatePrivateReport(any())).thenReturn(report);

        mockMvc.perform(get("/api/presents/{id}/private-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    void privateReportWithNonExistentIdShouldReturn404() throws Exception {
        when(reportService.generatePrivateReport(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/presents/{id}/private-report", newUUID()))
                .andExpect(status().isNotFound());
    }
}