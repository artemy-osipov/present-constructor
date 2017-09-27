package ru.home.shop.api.present;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class RemovePresentCommand {

    @TargetAggregateIdentifier
    UUID presentId;
}
