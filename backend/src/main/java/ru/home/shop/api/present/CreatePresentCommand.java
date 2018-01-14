package ru.home.shop.api.present;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor
public class CreatePresentCommand {

    UUID id;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItem> items;
}
