package ru.home.shop.api.candy;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.home.shop.query.candy.CandyEntry;

import java.math.BigDecimal;
import java.util.UUID;

@NonFinal
@Value
@AllArgsConstructor
class AbstractCandyEvent {

    private UUID id;
    private String name;
    private String firm;
    private double order;
    private BigDecimal price;

    AbstractCandyEvent(CandyEntry candy) {
        id = candy.getId();
        name = candy.getName();
        firm = candy.getFirm();
        order = candy.getOrder();
        price = candy.getPrice();
    }
}
