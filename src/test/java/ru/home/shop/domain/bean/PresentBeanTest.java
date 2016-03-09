package ru.home.shop.domain.bean;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PresentBeanTest {

    @Test
    public void getTruePrice_emptyCandies_shouldReturnZero() {
        assertEquals(BigDecimal.ZERO, new PresentBean().getTruePrice());
    }

    @Test
    public void getTruePrice_nonemptyCandies_shouldReturnSum() {
        PresentBean present = new PresentBean();

        CandyBean candy1 = new CandyBean();
        candy1.setPrice(BigDecimal.valueOf(1.1));
        candy1.setCount(1);

        CandyBean candy2 = new CandyBean();
        candy2.setPrice(BigDecimal.valueOf(2.2));
        candy2.setCount(2);

        present.getCandies().add(candy1);
        present.getCandies().add(candy2);

        assertEquals(BigDecimal.valueOf(5.5), present.getTruePrice());
    }
}