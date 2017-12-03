package ru.home.shop.query.present;

import org.junit.Test;
import ru.home.shop.query.candy.CandyEntry;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class PresentEntryTest {

    private PresentEntry getPresent() {
        PresentEntry present = new PresentEntry();
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(4.2));

        PresentItem item1 = new PresentItem();
        item1.setCandy(new CandyEntry());
        item1.getCandy().setId(newUUID());
        item1.getCandy().setName("name1");
        item1.getCandy().setFirm("firm1");
        item1.getCandy().setPrice(BigDecimal.valueOf(1.1));
        item1.setCount(2);

        PresentItem item2 = new PresentItem();
        item2.setCandy(new CandyEntry());
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
        PresentEntry emptyPresent = new PresentEntry();

        BigDecimal costPrice = emptyPresent.computeCost();

        assertThat(costPrice, equalTo(BigDecimal.ZERO));
    }

    @Test
    public void testComputeCostPrice() {
        PresentEntry present = getPresent();

        BigDecimal costPrice = present.computeCost();

        assertThat(costPrice, equalTo(new BigDecimal("15.4")));
    }
}