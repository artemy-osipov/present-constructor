package ru.home.shop.api.candy;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateCandyCommand extends AbstractCandyCommand {

    public CreateCandyCommand(UUID candyId, String name, String firm, double order, BigDecimal price) {
        super(candyId, name, firm, order, price);
    }
}
