package ru.home.shop.service.query.candy;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CandyQuery {

    UUID id;
    String name;
    String firm;
    BigDecimal price;
    double order;
}
