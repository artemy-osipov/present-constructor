package ru.home.shop.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PresentItemDTO {
    @NotNull
    private UUID id;
    @NotNull
    @Min(1)
    private Integer count;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
