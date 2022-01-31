package ru.home.shop.service.command.candy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.home.shop.service.candy.CandyCommandHandler
import ru.home.shop.service.candy.CreateCandyCommand
import ru.home.shop.service.candy.HideCandyCommand
import ru.home.shop.service.candy.UpdateCandyCommand

import static org.junit.jupiter.api.Assertions.assertThrows
import static ru.home.shop.utils.UuidUtils.newUUID

@ExtendWith(SpringExtension.class)
class CandyCommandHandlerIT {

    private static final UUID CANDY_ID = UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c");
    private static final String CANDY_NAME = "name";
    private static final String CANDY_FIRM = "firm";
    private static final BigDecimal CANDY_PRICE = BigDecimal.valueOf(2.6);
    private static final Double CANDY_ORDER = 1.1;

    @Autowired
    private CandyCommandHandler eventHandler;

    private CreateCandyCommand createCandyCommand() {
        return new CreateCandyCommand(CANDY_ID, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    private UpdateCandyCommand updateNotExistentCandyCommand() {
        return updateCandyCommand(newUUID());
    }

    private UpdateCandyCommand updateCandyCommand(UUID id) {
        return new UpdateCandyCommand(id, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    @Test
    void createCandyShouldInsertRecord() {
        eventHandler.on(createCandyCommand());
    }

    @Test
    void updateCandyUpdateRecord() {
        eventHandler.on(new UpdateCandyCommand(CANDY_ID, "name_updated", "firm_updated", BigDecimal.valueOf(3.6), 2.2));
    }

    @Test
    void updateNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(updateNotExistentCandyCommand()));
    }

    @Test
    void hideCandyShouldUpdateRecord() {
        eventHandler.on(new HideCandyCommand(CANDY_ID));
    }

    @Test
    void hideNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(new HideCandyCommand(newUUID())));
    }
}