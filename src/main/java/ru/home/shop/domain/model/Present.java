package ru.home.shop.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class Present extends Entity {

    private String name;
    private BigDecimal price;
    private Collection<Candy> candies = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Collection<Candy> getCandies() {
        return candies;
    }

    public void setCandies(Collection<Candy> candies) {
        this.candies = candies;
    }
}
