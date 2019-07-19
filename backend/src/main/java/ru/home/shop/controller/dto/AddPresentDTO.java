package ru.home.shop.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddPresentDTO {

    @NotNull
    @Size(min = 1, max = 50)
    String name;

    @NotNull
    @Min(1)
    @Digits(integer = 5, fraction = 2)
    BigDecimal price;

    @NotEmpty
    @Valid
    Collection<PresentItemDTO> items;
}
