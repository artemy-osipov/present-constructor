package ru.home.shop.service.candy;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Value
public class CreateCandyCommand {
    UUID id;
    String name;
    String firm;
    BigDecimal price;
    Double order;
}
