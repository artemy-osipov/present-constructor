package ru.home.shop.service;

import org.junit.Test;
import ru.home.shop.domain.Report;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.PresentItem;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class ReportServiceIT {

    private final static int PUBLIC_REPORT_LENGTH = 11632;
    private final static int PRIVATE_REPORT_LENGTH = 14572;
    private final static String REPORT_NAME = "name 4.2 RUB.docx";

    private final ReportService reportService = new ReportService();

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        PresentItem item1 = new PresentItem();
        item1.setCandy(new Candy());
        item1.getCandy().setId(newUUID());
        item1.getCandy().setName("name1");
        item1.getCandy().setFirm("firm1");
        item1.getCandy().setPrice(BigDecimal.valueOf(1.1));
        item1.setCount(2);

        PresentItem item2 = new PresentItem();
        item2.setCandy(new Candy());
        item2.getCandy().setId(newUUID());
        item2.getCandy().setName("name2");
        item2.getCandy().setFirm("firm2");
        item2.getCandy().setPrice(BigDecimal.valueOf(2.2));
        item2.setCount(6);

        present.setItems(asList(item1, item2));

        return present;
    }

    @Test
    public void generatePublicReportShouldGenerateSomeReport() {
        Report report = reportService.generatePublicReport(getPresent());

        assertThat(report.getName()).isEqualTo(REPORT_NAME);
        assertThat(report.getContent()).hasSize(PUBLIC_REPORT_LENGTH);
    }

    @Test
    public void generatePrivateReportShouldGenerateSomeReport() {
        Report report = reportService.generatePrivateReport(getPresent());

        assertThat(report.getName()).isEqualTo(REPORT_NAME);
        assertThat(report.getContent()).hasSize(PRIVATE_REPORT_LENGTH);
    }
}