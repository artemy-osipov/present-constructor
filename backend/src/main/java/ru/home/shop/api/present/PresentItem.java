package ru.home.shop.api.present;

import lombok.Value;

import java.util.UUID;

@Value
public class PresentItem {

    UUID id;
    UUID candyId;
    Integer count;
}
