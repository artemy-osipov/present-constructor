package io.github.artemy.osipov.shop.service.command.candy


import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

import static org.junit.jupiter.api.Assertions.assertThrows
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID

@ExtendWith(SpringExtension.class)
class CandyCommandHandlerIT {

    private static final UUID CANDY_ID = UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c");
    private static final String CANDY_NAME = "name";
    private static final String CANDY_FIRM = "firm";
    private static final BigDecimal CANDY_PRICE = BigDecimal.valueOf(2.6);
    private static final Double CANDY_ORDER = 1.1;

    @Autowired
    private io.github.artemy.osipov.shop.service.candy.CandyCommandHandler eventHandler;

    private io.github.artemy.osipov.shop.service.candy.CreateCandyCommand createCandyCommand() {
        return new io.github.artemy.osipov.shop.service.candy.CreateCandyCommand(CANDY_ID, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    private io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand updateNotExistentCandyCommand() {
        return updateCandyCommand(newUUID());
    }

    private io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand updateCandyCommand(UUID id) {
        return new io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand(id, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    @Test
    void createCandyShouldInsertRecord() {
        eventHandler.on(createCandyCommand());
    }

    @Test
    void updateCandyUpdateRecord() {
        eventHandler.on(new io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand(CANDY_ID, "name_updated", "firm_updated", BigDecimal.valueOf(3.6), 2.2));
    }

    @Test
    void updateNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(updateNotExistentCandyCommand()));
    }

    @Test
    void hideCandyShouldUpdateRecord() {
        eventHandler.on(new io.github.artemy.osipov.shop.service.candy.HideCandyCommand(CANDY_ID));
    }

    @Test
    void hideNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(new io.github.artemy.osipov.shop.service.candy.HideCandyCommand(newUUID())));
    }
}