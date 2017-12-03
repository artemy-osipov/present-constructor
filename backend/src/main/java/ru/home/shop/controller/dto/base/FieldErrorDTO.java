package ru.home.shop.controller.dto.base;

import lombok.Value;

@Value
public class FieldErrorDTO {

    String field;
    String message;
}
