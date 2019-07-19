package ru.home.shop.service.query.candy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandyQuery {

    UUID id;
    String name;
    String firm;
    BigDecimal price;
    double order;
}
