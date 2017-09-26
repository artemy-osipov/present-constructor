package ru.home.shop.domain.model;

import ru.home.shop.query.candy.CandyEntry;

public class PresentItem {

    private CandyEntry candy;
    private int count = 1;

    public CandyEntry getCandy() {
        return candy;
    }

    public void setCandy(CandyEntry candy) {
        this.candy = candy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
