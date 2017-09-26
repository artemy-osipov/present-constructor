package ru.home.shop.query.candy;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CandyEntry {

    UUID id;
    String name;
    String firm;
    double order;
    BigDecimal price;
    Boolean active;
}
