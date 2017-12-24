package ru.home.shop.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class EntityDTO {
    @NotNull
    UUID id;
}
