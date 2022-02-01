package io.github.artemy.osipov.shop.service


import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

import static org.assertj.core.api.Assertions.assertThat
import static org.junit.jupiter.api.Assertions.assertThrows
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID

@ExtendWith(SpringExtension.class)
class ReportServiceIT {

    private static final int PUBLIC_REPORT_LENGTH = 11632;
    private static final  int PRIVATE_REPORT_LENGTH = 14575;
    private static final int DELTA = 5;
    private static final String REPORT_NAME = "name 12.35 RUB.docx";

    private static final UUID PRESENT_ID = UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985");

    @Autowired
    private io.github.artemy.osipov.shop.service.report.ReportService reportService;

    @Test
    void generateReportWithNonExistentIdShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> reportService.generatePrivateReport(newUUID()));
    }

    @Test
    void generatePublicReportShouldGenerateSomeReport() {
        io.github.artemy.osipov.shop.service.report.Report report = reportService.generatePublicReport(PRESENT_ID);
        assertReport(report, REPORT_NAME, PUBLIC_REPORT_LENGTH);
    }

    @Test
    void generatePrivateReportShouldGenerateSomeReport() {
        io.github.artemy.osipov.shop.service.report.Report report = reportService.generatePrivateReport(PRESENT_ID);
        assertReport(report, REPORT_NAME, PRIVATE_REPORT_LENGTH);
    }

    private void assertReport(io.github.artemy.osipov.shop.service.report.Report report, String name, int size) {
        assertThat(report.getName()).isEqualTo(name);
        assertThat(report.getContent().length).isBetween(size - DELTA, size + DELTA);
    }
}