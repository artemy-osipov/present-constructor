package ru.home.shop.domain.model;

public class PresentItem {

    private Candy candy;
    private int count = 1;

    public Candy getCandy() {
        return candy;
    }

    public void setCandy(Candy candy) {
        this.candy = candy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
