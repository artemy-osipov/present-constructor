package ru.home.shop.query.present;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PresentQuery {

    UUID id;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItemQuery> items;
}
