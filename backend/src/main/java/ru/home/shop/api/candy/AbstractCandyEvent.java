package ru.home.shop.api.candy;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.util.UUID;

@NonFinal
@Value
public class AbstractCandyEvent {

    UUID id;
    String name;
    String firm;
    double order;
    BigDecimal price;
}
