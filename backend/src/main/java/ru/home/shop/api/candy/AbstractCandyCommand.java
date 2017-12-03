package ru.home.shop.api.candy;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

@NonFinal
@Value
class AbstractCandyCommand {

    @TargetAggregateIdentifier
    UUID candyId;
    String name;
    String firm;
    double order;
    BigDecimal price;
}
