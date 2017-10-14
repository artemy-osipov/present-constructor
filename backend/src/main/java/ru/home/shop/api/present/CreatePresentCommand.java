package ru.home.shop.api.present;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class CreatePresentCommand {

    @TargetAggregateIdentifier
    UUID presentId;
    String name;
    BigDecimal price;
    LocalDateTime date;
    List<PresentItem> items;
}
