package ru.home.shop.service.command.candy;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.HideCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.DBRiderIT;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.fromString;
import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyCommandHandlerIT extends DBRiderIT {

    private static final UUID CANDY_ID = fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c");
    private static final String CANDY_NAME = "name";
    private static final String CANDY_FIRM = "firm";
    private static final BigDecimal CANDY_PRICE = BigDecimal.valueOf(2.6);
    private static final Double CANDY_ORDER = 1.1;

    @Autowired
    private CandyCommandHandler eventHandler;

    public CandyCommandHandlerIT() {
        cleanDataAfterClass(CANDY);
    }

    private CreateCandyCommand createCandyCommand() {
        return new CreateCandyCommand(CANDY_ID, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    private UpdateCandyCommand updateCandyCommand() {
        return updateCandyCommand(CANDY_ID);
    }

    private UpdateCandyCommand updateNotExistentCandyCommand() {
        return updateCandyCommand(newUUID());
    }

    private UpdateCandyCommand updateCandyCommand(UUID id) {
        return new UpdateCandyCommand(id, CANDY_NAME, CANDY_FIRM, CANDY_PRICE, CANDY_ORDER);
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    @ExpectedDataSet("candy/candy.yml")
    public void createCandyShouldInsertRecord() {
        eventHandler.on(createCandyCommand());
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy.yml")
    public void updateCandyUpdateRecord() {
        eventHandler.on(updateCandyCommand());
    }

    @Test(expected = EntityNotFoundException.class)
    @DataSet("candy/candy_empty.yml")
    public void updateNonexistentEntityShouldThrowError() {
        eventHandler.on(updateNotExistentCandyCommand());
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy_not_active.yml")
    public void hideCandyShouldUpdateRecord() {
        eventHandler.on(new HideCandyCommand(CANDY_ID));
    }

    @Test(expected = EntityNotFoundException.class)
    @DataSet("candy/candy_empty.yml")
    public void hideNonexistentEntityShouldThrowError() {
        eventHandler.on(new HideCandyCommand(newUUID()));
    }
}