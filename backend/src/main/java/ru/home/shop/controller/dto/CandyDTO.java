package ru.home.shop.controller.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class CandyDTO {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    private String firm;

    @NotNull
    @Min(1)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Double order;
}
