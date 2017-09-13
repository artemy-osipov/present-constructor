package ru.home.shop.controller.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class EntityDTO {
    @NotNull
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
