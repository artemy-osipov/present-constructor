package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.service.PresentService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    private ReportServiceImpl reportService = new ReportServiceImpl();

    private PresentBean getPresent() {
        PresentBean present = new PresentBean();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        CandyBean candy1 = new CandyBean();
        candy1.setId(1);
        candy1.setName("name1");
        candy1.setFirm("firm1");
        candy1.setPrice(BigDecimal.valueOf(1.1));
        candy1.setCount(2);

        CandyBean candy2 = new CandyBean();
        candy2.setId(3);
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(2.2));
        candy2.setCount(6);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        return present;
    }

    @Test
    public void publicReportWithValidIdShouldGenerateSomeReport() throws IOException {
        assertNotNull(reportService.publicReport(getPresent()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void publicReportWithNotValidIdShouldThrowException() throws IOException {
        PresentService mock = mock(PresentService.class);
        int id = 1;
        when(mock.find(id)).thenReturn(null);

        reportService.publicReport(null);
    }

    @Test
    public void privateReportWithVlidIdShouldGenerateSomeReport() throws IOException {
        assertNotNull(reportService.privateReport(getPresent()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void privateWithNotValidIdShouldThrowException() throws IOException {
        reportService.privateReport(null);
    }

    @Test
    public void presentWithoutCandiesHasZeroCostPrice() {
        assertEquals(BigDecimal.ZERO, reportService.computeCostPrice(new PresentBean()));
    }

    @Test
    public void testComputeCostPrice() {
        assertEquals(new BigDecimal("15.4"), reportService.computeCostPrice(getPresent()));
    }
}