package ru.home.shop.api.candy;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateCandyCommand extends AbstractCandyCommand {

    public CreateCandyCommand(UUID candyId, String name, String firm, BigDecimal price, Double order) {
        super(candyId, name, firm, price, order);
    }
}
