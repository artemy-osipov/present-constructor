package io.github.artemy.osipov.shop.service.report

import io.github.artemy.osipov.shop.testdata.ReportTestData
import org.junit.jupiter.api.Test

class ReportPresentTest {

    @Test
    void 'should compute cost'() {
        def present = ReportTestData.reportPresent()

        BigDecimal cost = present.computeCost()

        assert cost == 21.0
    }
}
