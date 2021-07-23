package ru.home.shop.service.present

import org.junit.jupiter.api.Test
import ru.home.shop.testdata.PresentTestData

class PresentTest {

    @Test
    void 'should compute cost'() {
        Present present = PresentTestData.present()

        BigDecimal cost = present.computeCost()

        assert cost == 5.5
    }
}
