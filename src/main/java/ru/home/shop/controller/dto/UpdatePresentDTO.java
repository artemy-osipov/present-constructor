package ru.home.shop.controller.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;

public class UpdatePresentDTO {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    @Min(1)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
    @NotEmpty
    @Valid
    private Collection<PresentItemDTO> items;

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

    public Collection<PresentItemDTO> getItems() {
        return items;
    }

    public void setItems(Collection<PresentItemDTO> items) {
        this.items = items;
    }
}
