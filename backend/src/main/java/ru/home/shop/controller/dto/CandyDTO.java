package ru.home.shop.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandyDTO {

    @NotNull
    @Size(min = 1, max = 50)
    String name;

    @NotNull
    @Size(min = 1, max = 50)
    String firm;

    @NotNull
    @Min(1)
    @Digits(integer = 5, fraction = 2)
    BigDecimal price;

    @NotNull
    @Min(1)
    Double order;
}
