package ru.home.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.service.PresentService;
import ru.home.shop.service.ReportService;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PresentsApplication.class)
public class ReportControllerTest {

    private MockMvc getMockMvc(PresentService presentService, ReportService reportService) {
        return MockMvcBuilders.standaloneSetup(new ReportController(presentService, reportService)).build();
    }

    private PresentBean getPresent() {
        PresentBean present = new PresentBean();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(2.4));

        return present;
    }

    @Test
    public void publicReport_existentId_shouldReturnReport() throws Exception {
        int presentId = 1;
        PresentBean present = getPresent();
        byte[] report = {1, 2, 3};
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(present).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).publicReport(present);

        byte[] response = getMockMvc(mockPresent, mockReport).perform(get("/present/publicReport/{id}", presentId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(String.format("attachment; filename*=UTF-8''%s%%20%s%%20RUB.docx", present.getName(), present.getPrice()))))
                .andReturn().getResponse().getContentAsByteArray();

        verify(mockPresent).find(presentId);
        verify(mockReport).publicReport(present);
        assertArrayEquals(report, response);
    }

    @Test
    public void publicReport_nonexistentId_shouldReturn404() throws Exception {
        int presentId = 1;
        PresentBean present = getPresent();
        byte[] report = {1, 2, 3};
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(null).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).publicReport(present);

        getMockMvc(mockPresent, mockReport).perform(get("/present/publicReport/{id}", presentId))
                .andExpect(status().isNotFound());

        verify(mockPresent).find(presentId);
    }

    @Test
    public void privateReport_existentId_shouldReturnReport() throws Exception {
        int presentId = 1;
        PresentBean present = getPresent();
        byte[] report = {1, 2, 3};
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(present).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).privateReport(present);

        byte[] response = getMockMvc(mockPresent, mockReport).perform(get("/present/privateReport/{id}", presentId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", is(String.format("attachment; filename*=UTF-8''%s%%20%s%%20RUB.docx", present.getName(), present.getPrice()))))
                .andReturn().getResponse().getContentAsByteArray();

        verify(mockPresent).find(presentId);
        verify(mockReport).privateReport(present);
        assertArrayEquals(report, response);
    }

    @Test
    public void privateReport_nonexistentId_shouldReturn404() throws Exception {
        int presentId = 1;
        PresentBean present = getPresent();
        byte[] report = {1, 2, 3};
        PresentService mockPresent = mock(PresentService.class);
        ReportService mockReport = mock(ReportService.class);

        doReturn(null).when(mockPresent).find(presentId);
        doReturn(report).when(mockReport).publicReport(present);

        getMockMvc(mockPresent, mockReport).perform(get("/present/privateReport/{id}", presentId))
                .andExpect(status().isNotFound());

        verify(mockPresent).find(presentId);
    }
}