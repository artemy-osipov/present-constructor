package ru.home.shop.controller.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PresentItemDTO {
    @NotNull
    @Valid
    private EntityDTO candy;
    @NotNull
    @Min(1)
    private Integer count;

    public EntityDTO getCandy() {
        return candy;
    }

    public void setCandy(EntityDTO candy) {
        this.candy = candy;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
