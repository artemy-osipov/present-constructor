package ru.home.shop.controller.dto.base;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldErrorDTO {

    String field;
    String message;
}
