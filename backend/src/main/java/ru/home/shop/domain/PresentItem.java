package ru.home.shop.domain;

import java.util.UUID;

public class PresentItem {

    private UUID candyId;
    private Integer count;

    public PresentItem(UUID candyId, Integer count) {
        this.candyId = candyId;
        this.count = count;
    }
}
