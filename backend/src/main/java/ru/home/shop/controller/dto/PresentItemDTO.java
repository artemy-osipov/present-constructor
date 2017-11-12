package ru.home.shop.controller.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PresentItemDTO {
    @NotNull
    @Valid
    private EntityDTO candy;

    @NotNull
    @Min(1)
    private Integer count;
}
