package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;
import ru.home.shop.service.PresentService;
import ru.home.shop.service.ReportService;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportQueryControllerIT {

    private final ReportService reportService = mock(ReportService.class);
    private final PresentService presentService = mock(PresentService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new ReportQueryController(presentService, reportService))
            .setControllerAdvice(new ErrorHandler())
            .build();

    private final static String CONTENT_DISPOSITION = "form-data; name=\"attachment\"; filename*=UTF-8''name%202.4%20RUB.docx";

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(2.4));

        return present;
    }

    private Report getReport() {
        return new Report("name 2.4 RUB.docx", new byte[] {1, 2, 3});
    }

    @Test
    public void publicReportWithExistentIdShouldReturnReport() throws Exception {
        Present present = getPresent();
        Report report = getReport();

        doReturn(present).when(presentService).find(any());
        doReturn(report).when(reportService).generatePublicReport(present);

        mockMvc.perform(get("/presents/{id}/public-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    public void publicReportWithNonExistentIdShouldReturn404() throws Exception {
        doReturn(null).when(presentService).find(any());

        mockMvc.perform(get("/presents/{id}/public-report", newUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void privateReportWithExistentIdShouldReturnReport() throws Exception {
        Present present = getPresent();
        Report report = getReport();

        doReturn(present).when(presentService).find(any());
        doReturn(report).when(reportService).generatePrivateReport(present);

        mockMvc.perform(get("/presents/{id}/private-report", newUUID()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(CONTENT_DISPOSITION)))
                .andExpect(content().bytes(report.getContent()));
    }

    @Test
    public void privateReportWithNonExistentIdShouldReturn404() throws Exception {
        doReturn(null).when(presentService).find(any());

        mockMvc.perform(get("/presents/{id}/private-report", newUUID()))
                .andExpect(status().isNotFound());
    }
}