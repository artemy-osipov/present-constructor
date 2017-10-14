package ru.home.shop.api.present;

import lombok.Value;

import java.util.UUID;

@Value
public class PresentItem {

    UUID candyId;
    Integer count;
}
