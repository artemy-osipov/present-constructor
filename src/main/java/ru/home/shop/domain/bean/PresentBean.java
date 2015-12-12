package ru.home.shop.domain.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public BigDecimal getTruePrice() {
        if (candies == null) {
            return BigDecimal.ZERO;
        } else {
            return candies.stream()
                    .map(c ->
                            (c.getPrice() == null ? BigDecimal.ZERO : c.getPrice())
                            .multiply(BigDecimal.valueOf(c.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PresentBean that = (PresentBean) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return !(candies != null ? !candies.equals(that.candies) : that.candies != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
