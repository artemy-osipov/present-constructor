package io.github.artemy.osipov.shop.testdata

import groovy.transform.CompileStatic
import io.github.artemy.osipov.shop.service.report.Report
import io.github.artemy.osipov.shop.service.report.ReportPresent

@CompileStatic
class ReportTestData {

    static Report report() {
        new Report("name 2.4 RUB.docx", new byte[]{1, 2, 3})
    }

    static ReportPresent reportPresent() {
        new ReportPresent(
                PresentTestData.present(),
                [CandyTestData.candy()]
        )
    }
}
