package ru.home.shop.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PresentItemDTO {
    @NotNull
    private UUID candyId;
    @NotNull
    @Min(1)
    private Integer count;

    public UUID getCandyId() {
        return candyId;
    }

    public void setCandyId(UUID candyId) {
        this.candyId = candyId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
