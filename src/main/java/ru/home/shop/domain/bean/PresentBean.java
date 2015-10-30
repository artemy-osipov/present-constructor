package ru.home.shop.domain.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class PresentBean {

    private int id;
    private String name;
    private BigDecimal price;
    private Collection<CandyBean> candies = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Collection<CandyBean> getCandies() {
        return candies;
    }

    public void setCandies(Collection<CandyBean> candies) {
        this.candies = candies;
    }
}
