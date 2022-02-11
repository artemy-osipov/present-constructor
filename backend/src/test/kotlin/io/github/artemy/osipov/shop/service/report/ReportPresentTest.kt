package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.testdata.ReportTestData
import org.junit.jupiter.api.Test

class ReportPresentTest {

    @Test
    fun `should compute cost`() {
        val present = ReportTestData.reportPresent()

        val cost = present.computeCost()

        assert(cost.toDouble() == 21.0)
    }
}
