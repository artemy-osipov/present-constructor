package ru.home.shop.service.command.candy;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.HideCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.utils.db.DBTest;
import ru.home.shop.utils.db.ExpectedQueryCount;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@DBTest
@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = CandyCommandHandler.class))
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
    @DataSet("candy/candy_empty.yml")
    @ExpectedDataSet("candy/candy.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.INSERT, count = 1)
            })
    void createCandyShouldInsertRecord() {
        eventHandler.on(createCandyCommand());
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy_updated.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.UPDATE, count = 1)
            })
    void updateCandyUpdateRecord() {
        eventHandler.on(new UpdateCandyCommand(CANDY_ID, "name_updated", "firm_updated", BigDecimal.valueOf(3.6), 2.2));
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.UPDATE, count = 0)
            })
    void updateNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(updateNotExistentCandyCommand()));
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy_not_active.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.UPDATE, count = 1)
            })
    void hideCandyShouldUpdateRecord() {
        eventHandler.on(new HideCandyCommand(CANDY_ID));
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.UPDATE, count = 0)
            })
    void hideNonexistentEntityShouldThrowException() {
        assertThrows(EntityNotFoundException.class, () -> eventHandler.on(new HideCandyCommand(newUUID())));
    }
}