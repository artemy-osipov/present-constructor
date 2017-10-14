package ru.home.shop.api.candy;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class RemoveCandyCommand {

    @TargetAggregateIdentifier
    UUID candyId;
}
