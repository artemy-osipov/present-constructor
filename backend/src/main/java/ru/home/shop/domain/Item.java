package ru.home.shop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Getter
@Embeddable
public class Item {

    @ManyToOne
    private Candy candy;
    private Integer count;

    Item() {
    }

    public Item(Candy candy, Integer count) {
        this.candy = candy;
        this.count = count;
    }
}
