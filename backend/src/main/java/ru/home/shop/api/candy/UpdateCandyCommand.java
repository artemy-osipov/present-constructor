package ru.home.shop.api.candy;

import ru.home.shop.domain.Candy;

import java.math.BigDecimal;
import java.util.UUID;

public class UpdateCandyCommand extends AbstractCandyCommand {

    public UpdateCandyCommand(UUID candyId, String name, String firm, double order, BigDecimal price) {
        super(candyId, name, firm, order, price);
    }

    public UpdateCandyCommand(Candy candy) {
        super(candy);
    }
}
