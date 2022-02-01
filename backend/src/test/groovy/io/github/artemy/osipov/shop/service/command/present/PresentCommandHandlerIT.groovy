package io.github.artemy.osipov.shop.service.command.present

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.test.context.junit.jupiter.SpringExtension
import io.github.artemy.osipov.present.service.present.CreatePresentCommand
import io.github.artemy.osipov.present.service.present.PresentCommandHandler
import io.github.artemy.osipov.present.service.present.RemovePresentCommand

import java.time.LocalDateTime

import static org.junit.jupiter.api.Assertions.assertThrows
import static io.github.artemy.osipov.present.utils.UuidUtils.newUUID

@ExtendWith(SpringExtension.class)
class PresentCommandHandlerIT {

    private static final UUID PRESENT_ID = UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985");
    private static final String PRESENT_NAME = "name";
    private static final BigDecimal PRESENT_PRICE = BigDecimal.valueOf(12.35);
    private static final LocalDateTime PRESENT_DATE = LocalDateTime.of(2017, 1, 1, 12, 0);

    @Autowired
    private PresentCommandHandler eventHandler;

    private CreatePresentCommand createPresentCommand() {
        List<CreatePresentCommand.PresentItem> items = new ArrayList<>();
        items.add(new CreatePresentCommand.PresentItem(UUID.fromString("a764c765-483c-492b-ac63-4f2c4f6d2ff4"), 2));
        items.add(new CreatePresentCommand.PresentItem(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"), 6));

        return new CreatePresentCommand(PRESENT_ID, PRESENT_NAME, PRESENT_PRICE, PRESENT_DATE, items);
    }

    @Test
    void addPresentShouldInsertRecord() {
        eventHandler.on(createPresentCommand());
    }

    @Test
    void removeExistentEntityShouldRemoveRecord() {
        eventHandler.on(new RemovePresentCommand(PRESENT_ID));
    }

    @Test
    void removeNonexistentEntityShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> eventHandler.on(new RemovePresentCommand(newUUID())));
    }
}