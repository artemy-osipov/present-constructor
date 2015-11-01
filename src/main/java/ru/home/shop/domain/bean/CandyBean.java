package ru.home.shop.domain.bean;

import java.math.BigDecimal;

public class CandyBean {

    private Integer id;
    private String name;
    private String firm;
    private double order;
    private BigDecimal price;
    private int count = 1;

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

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandyBean candyBean = (CandyBean) o;

        if (Double.compare(candyBean.order, order) != 0) return false;
        if (count != candyBean.count) return false;
        if (id != null ? !id.equals(candyBean.id) : candyBean.id != null) return false;
        if (name != null ? !name.equals(candyBean.name) : candyBean.name != null) return false;
        if (firm != null ? !firm.equals(candyBean.firm) : candyBean.firm != null) return false;
        return !(price != null ? !price.equals(candyBean.price) : candyBean.price != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (firm != null ? firm.hashCode() : 0);
        return result;
    }
}
