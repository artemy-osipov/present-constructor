package ru.home.shop.service.impl;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;
import ru.home.shop.service.PresentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    private ReportServiceImpl reportService = new ReportServiceImpl();

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(Generators.timeBasedGenerator().generate());
        candy1.setName("name1");
        candy1.setFirm("firm1");
        candy1.setPrice(BigDecimal.valueOf(1.1));
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setId(Generators.timeBasedGenerator().generate());
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
        Report report = reportService.publicReport(getPresent());

        assertEquals("name 4.2 RUB.docx", report.getName());
        assertEquals(11632, report.getContent().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void publicReportWithNotValidIdShouldThrowException() throws IOException {
        PresentService mock = mock(PresentService.class);
        UUID id = Generators.timeBasedGenerator().generate();
        when(mock.find(id)).thenReturn(null);

        reportService.publicReport(null);
    }

    @Test
    public void privateReportWithValidIdShouldGenerateSomeReport() throws IOException {
        Report report = reportService.privateReport(getPresent());

        assertEquals("name 4.2 RUB.docx", report.getName());
        assertEquals(14572, report.getContent().length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void privateWithNotValidIdShouldThrowException() throws IOException {
        reportService.privateReport(null);
    }

    @Test
    public void presentWithoutCandiesHasZeroCostPrice() {
        assertEquals(BigDecimal.ZERO, reportService.computeCostPrice(new Present()));
    }

    @Test
    public void testComputeCostPrice() {
        assertEquals(new BigDecimal("15.4"), reportService.computeCostPrice(getPresent()));
    }
}