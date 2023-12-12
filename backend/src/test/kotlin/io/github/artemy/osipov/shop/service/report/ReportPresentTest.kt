package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.testdata.ReportTD
import org.junit.jupiter.api.Test

class ReportPresentTest {

    @Test
    fun `should compute cost`() {
        val present = ReportTD.reportPresent()

        val cost = present.computeCost()

        assert(cost.toDouble() == 21.0)
    }
}
