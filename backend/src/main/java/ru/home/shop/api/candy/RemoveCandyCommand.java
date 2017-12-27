package ru.home.shop.api.candy;

import lombok.Value;

import java.util.UUID;

@Value
public class RemoveCandyCommand {

    UUID id;
}
