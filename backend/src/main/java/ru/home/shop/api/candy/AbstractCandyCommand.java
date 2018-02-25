package ru.home.shop.api.candy;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.math.BigDecimal;
import java.util.UUID;

@NonFinal
@Value
@AllArgsConstructor
class AbstractCandyCommand {

    private UUID id;
    private String name;
    private String firm;
    private BigDecimal price;
    private Double order;
}
