package ru.home.shop.service.impl;

import org.junit.Test;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.PresentItem;
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

        present.getItems().add(item1);
        present.getItems().add(item2);

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
}