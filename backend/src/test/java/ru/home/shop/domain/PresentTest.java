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

        PresentItem item1 = new PresentItem();
        item1.setCandy(new Candy());
        item1.getCandy().setId(newUUID());
        item1.getCandy().setName("name1");
        item1.getCandy().setFirm("firm1");
        item1.getCandy().setPrice(BigDecimal.valueOf(1.1));
        item1.setCount(2);

        PresentItem item2 = new PresentItem();
        item2.setCandy(new Candy());
        item2.getCandy().setId(newUUID());
        item2.getCandy().setName("name2");
        item2.getCandy().setFirm("firm2");
        item2.getCandy().setPrice(BigDecimal.valueOf(2.2));
        item2.setCount(6);

        present.getItems().add(item1);
        present.getItems().add(item2);

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