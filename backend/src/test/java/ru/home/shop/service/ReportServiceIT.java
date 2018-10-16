package ru.home.shop.service;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.home.shop.domain.Report;
import ru.home.shop.utils.db.DBTest;
import ru.home.shop.utils.db.DatabaseConfig;
import ru.home.shop.utils.db.ExpectedQueryCount;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@DBTest
@ContextHierarchy({
        @ContextConfiguration(classes = DatabaseConfig.class),
        @ContextConfiguration(classes = ReportService.class)
})
class ReportServiceIT {

    private static final int PUBLIC_REPORT_LENGTH = 11632;
    private static final  int PRIVATE_REPORT_LENGTH = 14575;
    private static final int DELTA = 5;
    private static final String REPORT_NAME = "name 12.35 RUB.docx";

    private static final UUID PRESENT_ID = UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985");

    @Autowired
    private ReportService reportService;

    @Test
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1)
            })
    void generateReportWithNonExistentIdShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> reportService.generatePrivateReport(newUUID()));
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 2)
            })
    void generatePublicReportShouldGenerateSomeReport() {
        Report report = reportService.generatePublicReport(PRESENT_ID);
        assertReport(report, REPORT_NAME, PUBLIC_REPORT_LENGTH);
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 2)
            })
    void generatePrivateReportShouldGenerateSomeReport() {
        Report report = reportService.generatePrivateReport(PRESENT_ID);
        assertReport(report, REPORT_NAME, PRIVATE_REPORT_LENGTH);
    }

    private void assertReport(Report report, String name, int size) {
        assertThat(report.getName()).isEqualTo(name);
        assertThat(report.getContent().length).isBetween(size - DELTA, size + DELTA);
    }
}