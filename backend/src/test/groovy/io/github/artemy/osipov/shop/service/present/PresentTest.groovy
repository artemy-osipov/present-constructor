package io.github.artemy.osipov.shop.service.present


import org.junit.jupiter.api.Test

class PresentTest {

    @Test
    void 'should compute cost'() {
        Present present = io.github.artemy.osipov.shop.testdata.PresentTestData.present()

        BigDecimal cost = present.computeCost()

        assert cost == 5.5
    }
}
