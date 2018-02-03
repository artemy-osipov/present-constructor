package ru.home.shop.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.Report;
import ru.home.shop.service.ReportService;
import ru.home.shop.service.command.present.PresentRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportQueryController.class)
@WithMockUser
class ReportQueryControllerIT {

    @MockBean
    private ReportService reportService;
    @MockBean
    private PresentRepository repository;

    @Autowired
    private MockMvc mockMvc;

    private final static String CONTENT_DISPOSITION = "form-data; name=\"attachment\"; filename*=UTF-8''name%202.4%20RUB.docx";

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(2.4));

        return present;
    }

    private Report getReport() {
        return new Report("name 2.4 RUB.docx", new byte[]{1, 2, 3});
    }

    @Disabled("migrate to spring security 5")
    @Test
    @WithAnonymousUser
    void publicReportWithWithAnonymousUserShouldReturn401() throws Exception {
        Present present = getPresent();
        Report report = getReport();

        doReturn(present).when(repository).findById(any());
        doReturn(report).when(reportService).generatePublicReport(present);

        mockMvc.perform(get("/api/presents/{id}/public-report", newUUID()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void publicReportWithExistentIdShouldReturnReport() throws Exception {
        Present present = getPresent();
        Report report = getReport();

        when(repository.findById(any())).thenReturn(Optional.of(present));
        when(reportService.generatePublicReport(present)).thenReturn(report);

        mockMvc.perform(get("/api/presents/{id}/public-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    void publicReportWithNonExistentIdShouldReturn404() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/presents/{id}/public-report", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void privateReportWithExistentIdShouldReturnReport() throws Exception {
        Present present = getPresent();
        Report report = getReport();

        when(repository.findById(any())).thenReturn(Optional.of(present));
        when(reportService.generatePrivateReport(present)).thenReturn(report);

        mockMvc.perform(get("/api/presents/{id}/private-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    void privateReportWithNonExistentIdShouldReturn404() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/presents/{id}/private-report", newUUID()))
                .andExpect(status().isNotFound());
    }
}