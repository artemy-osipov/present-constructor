package ru.home.shop.service;

import org.junit.Test;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.Report;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class ReportServiceIT {

    private final static int PUBLIC_REPORT_LENGTH = 11632;
    private final static int PRIVATE_REPORT_LENGTH = 14575;
    private final static int DELTA = 5;
    private final static String REPORT_NAME = "name 4.2 RUB.docx";

    private final ReportService reportService = new ReportService();

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(newUUID());
        candy1.setName("name1");
        candy1.setFirm("firm1");
        candy1.setPrice(BigDecimal.valueOf(1.1));

        present.getItems().put(candy1, 2);

        Candy candy2 = new Candy();
        candy2.setId(newUUID());
        candy2.setName("name2");
        candy2.setFirm("firm2");
        candy2.setPrice(BigDecimal.valueOf(2.2));

        present.getItems().put(candy2, 6);

        return present;
    }

    @Test
    public void generatePublicReportShouldGenerateSomeReport() {
        Report report = reportService.generatePublicReport(getPresent());

        assertThat(report.getName()).isEqualTo(REPORT_NAME);
        assertThat(report.getContent().length).isBetween(PUBLIC_REPORT_LENGTH - DELTA, PUBLIC_REPORT_LENGTH + DELTA);
    }

    @Test
    public void generatePrivateReportShouldGenerateSomeReport() {
        Report report = reportService.generatePrivateReport(getPresent());

        assertThat(report.getName()).isEqualTo(REPORT_NAME);
        assertThat(report.getContent().length).isBetween(PRIVATE_REPORT_LENGTH - DELTA, PRIVATE_REPORT_LENGTH + DELTA);
    }
}