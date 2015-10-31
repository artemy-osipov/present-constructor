package ru.home.shop.domain.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class PresentBean {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Collection<CandyBean> candies = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getTruePrice() {
        return candies.stream().map(CandyBean::getPrice).reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2));
    }
}
