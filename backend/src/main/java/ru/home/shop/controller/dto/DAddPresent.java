package ru.home.shop.controller.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;

@Data
public class DAddPresent {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Min(1)
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;

    @NotEmpty
    @Valid
    private Collection<DPresentItem> items;
}
