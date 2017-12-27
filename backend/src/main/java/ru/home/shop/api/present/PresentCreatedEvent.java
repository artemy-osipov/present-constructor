package ru.home.shop.api.present;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.home.shop.query.present.PresentEntry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor
public class PresentCreatedEvent {

    UUID id;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItem> items;

    public PresentCreatedEvent(PresentEntry present) {
        id = present.getId();
        name = present.getName();
        price = present.getPrice();
        date = present.getDate();
        items = present.getItems().stream()
                .map(item -> new PresentItem(item.getId(), item.getCandy().getId(), item.getCount()))
                .collect(Collectors.toList());
    }
}
