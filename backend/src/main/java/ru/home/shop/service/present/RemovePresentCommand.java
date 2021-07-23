package ru.home.shop.service.present;

import lombok.Value;

import java.util.UUID;

@Value
public class RemovePresentCommand {
    UUID id;
}
