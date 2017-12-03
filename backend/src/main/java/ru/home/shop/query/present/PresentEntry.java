package ru.home.shop.query.present;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PresentEntry {

    UUID id;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItem> items = new ArrayList<>();

    public BigDecimal computeCost() {
        return getItems().stream()
                .map(c -> c.getCandy().getPrice().multiply(BigDecimal.valueOf(c.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
