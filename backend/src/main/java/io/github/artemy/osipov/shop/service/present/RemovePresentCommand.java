package io.github.artemy.osipov.shop.service.present;

import lombok.Value;

import java.util.UUID;

@Value
public class RemovePresentCommand {
    UUID id;
}
