package ru.home.shop.service.query.present;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PresentQuery {

    UUID id;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItemQuery> items;
}
