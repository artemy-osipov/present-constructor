package ru.home.shop.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class PresentTest {

    private Present getPresent() {
        Present present = new Present();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        Candy candy1 = new Candy();
        candy1.setId(newUUID());
        candy1.setPrice(BigDecimal.valueOf(1.1));

        present.getItems().put(candy1, 2);

        Candy candy2 = new Candy();
        candy2.setId(newUUID());
        candy2.setPrice(BigDecimal.valueOf(2.2));

        present.getItems().put(candy2, 6);

        return present;
    }

    @Test
    public void emptyPresentShouldHasZeroCostPrice() {
        Present emptyPresent = new Present();

        BigDecimal costPrice = emptyPresent.computeCost();

        assertThat(costPrice).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testComputeCostPrice() {
        Present present = getPresent();

        BigDecimal costPrice = present.computeCost();

        assertThat(costPrice).isEqualTo(new BigDecimal("15.4"));
    }
}