package io.github.artemy.osipov.shop.testdata

import io.github.artemy.osipov.shop.service.report.Report
import io.github.artemy.osipov.shop.service.report.ReportPresent

object ReportTestData {

    val REPORT_NAME = "name 42.25 RUB.docx"

    fun report(): Report {
        return Report(REPORT_NAME, byteArrayOf(1, 2, 3))
    }

    fun reportPresent(): ReportPresent {
        return ReportPresent(
                PresentTestData.present(),
                listOf(CandyTestData.candy())
        )
    }
}
