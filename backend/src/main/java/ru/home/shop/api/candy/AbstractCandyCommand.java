package ru.home.shop.api.candy;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.home.shop.domain.Candy;

import java.math.BigDecimal;
import java.util.UUID;

@NonFinal
@Value
@AllArgsConstructor
class AbstractCandyCommand {

    UUID id;
    String name;
    String firm;
    double order;
    BigDecimal price;

    AbstractCandyCommand(Candy candy) {
        id = candy.getId();
        name = candy.getName();
        firm = candy.getFirm();
        order = candy.getOrder();
        price = candy.getPrice();
    }
}
