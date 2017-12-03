package ru.home.shop.api.candy;

import java.math.BigDecimal;
import java.util.UUID;

public class CandyCreatedEvent extends AbstractCandyEvent {

    public CandyCreatedEvent(UUID id, String name, String firm, double order, BigDecimal price) {
        super(id, name, firm, order, price);
    }
}
