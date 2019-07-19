package ru.home.shop.controller.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PresentItemDTO {

    @NotNull
    @Valid
    EntityDTO candy;

    @NotNull
    @Min(1)
    Integer count;
}
