package ru.home.shop.controller.command;

import java.util.UUID;

public class DeleteCandyCommand {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
