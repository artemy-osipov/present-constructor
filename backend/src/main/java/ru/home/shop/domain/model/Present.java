package ru.home.shop.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Present extends Entity {

    private String name;
    private BigDecimal price;
    private LocalDateTime date;
    private List<PresentItem> items;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<PresentItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }

        return items;
    }

    public void setItems(List<PresentItem> candies) {
        this.items = candies;
    }

    public BigDecimal computeCost() {
        return getItems().stream()
                .map(c -> c.getCandy().getPrice().multiply(BigDecimal.valueOf(c.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
