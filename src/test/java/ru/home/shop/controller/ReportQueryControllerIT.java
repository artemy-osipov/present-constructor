package ru.home.shop.controller;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;
import ru.home.shop.service.PresentService;
import ru.home.shop.service.ReportService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PresentsApplication.class)
public class ReportQueryControllerIT {

    private final ReportService reportService = mock(ReportService.class);
    private final PresentService presentService = mock(PresentService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new ReportQueryController(presentService, reportService))
            .setControllerAdvice(new ErrorHandler())
            .build();

    private MockMvc getMockMvc(PresentService presentService, ReportService reportService) {
        return MockMvcBuilders.standaloneSetup(new ReportQueryController(presentService, reportService)).build();
    }

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
        UUID presentId = Generators.timeBasedGenerator().generate();
        Present present = getPresent();
        Report report = getReport();
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(present).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).publicReport(present);

        byte[] response = getMockMvc(mockPresent, mockReport).perform(get("/presents/{id}/publicReport", presentId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(String.format("attachment; filename*=UTF-8''%s%%20%s%%20RUB.docx", present.getName(), present.getPrice()))))
                .andReturn().getResponse().getContentAsByteArray();

        assertArrayEquals(report.getContent(), response);
    }

    @Test
    public void publicReportWithNonExistentIdShouldReturn404() throws Exception {
        UUID presentId = Generators.timeBasedGenerator().generate();

        doReturn(null).when(presentService).find(presentId);

        mockMvc.perform(get("/presents/{id}/publicReport", presentId))
                .andExpect(status().isNotFound());

        verify(presentService).find(presentId);
    }

    @Test
    public void privateReportWithExistentIdShouldReturnReport() throws Exception {
        UUID presentId = Generators.timeBasedGenerator().generate();
        Present present = getPresent();
        Report report = getReport();
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(present).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).privateReport(present);

        byte[] response = getMockMvc(mockPresent, mockReport).perform(get("/presents/{id}/privateReport", presentId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(String.format("attachment; filename*=UTF-8''%s%%20%s%%20RUB.docx", present.getName(), present.getPrice()))))
                .andReturn().getResponse().getContentAsByteArray();

        assertArrayEquals(report.getContent(), response);
    }

    @Test
    public void privateReportWithNonExistentIdShouldReturn404() throws Exception {
        UUID presentId = Generators.timeBasedGenerator().generate();
        PresentService mockPresent = mock(PresentService.class);

        doReturn(null).when(mockPresent).find(presentId);

        mockMvc.perform(get("/presents/{id}/privateReport", presentId))
                .andExpect(status().isNotFound());
    }
}