package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.Report;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class ReportServiceImplTest {

    private final static int PUBLIC_REPORT_LENGTH = 11632;
    private final static int PRIVATE_REPORT_LENGTH = 14572;
    private final static String REPORT_NAME = "name 4.2 RUB.docx";

    private final ReportServiceImpl reportService = new ReportServiceImpl();

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(newUUID());
        candy1.setName("name1");
        candy1.setFirm("firm1");
        candy1.setPrice(BigDecimal.valueOf(1.1));
        candy1.setCount(2);

        Candy candy2 = new Candy();
        candy2.setId(newUUID());
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(2.2));
        candy2.setCount(6);

        present.getItems().add(candy1);
        present.getItems().add(candy2);

        return present;
    }

    @Test
    public void generatePublicReportShouldGenerateSomeReport() {
        Report report = reportService.generatePublicReport(getPresent());

        assertThat(report.getName(), equalTo(REPORT_NAME));
        assertThat(report.getContent().length, equalTo(PUBLIC_REPORT_LENGTH));
    }

    @Test
    public void generatePrivateReportShouldGenerateSomeReport() {
        Report report = reportService.generatePrivateReport(getPresent());

        assertThat(report.getName(), equalTo(REPORT_NAME));
        assertThat(report.getContent().length, equalTo(PRIVATE_REPORT_LENGTH));
    }

    @Test
    public void emptyPresentShouldHasZeroCostPrice() {
        Present emptyPresent = new Present();

        BigDecimal costPrice = reportService.computeCostPrice(emptyPresent);

        assertThat(costPrice, equalTo(BigDecimal.ZERO));
    }

    @Test
    public void testComputeCostPrice() {
        Present present = getPresent();

        BigDecimal costPrice = reportService.computeCostPrice(present);

        assertThat(costPrice, equalTo(new BigDecimal("15.4")));
    }
}