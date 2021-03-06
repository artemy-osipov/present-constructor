package ru.home.shop.service.command.present;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.utils.db.DBTest;
import ru.home.shop.utils.db.ExpectedQueryCount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@DBTest
@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = PresentCommandHandler.class))
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
    @DataSet({"present/present_empty.yml", "candy/candy_list.yml"})
    @ExpectedDataSet("present/present.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 2),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.INSERT, count = 2)
            })
    void addPresentShouldInsertRecord() {
        eventHandler.on(createPresentCommand());
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    @ExpectedDataSet("present/present_empty.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.DELETE, count = 2)
            })
    void removeExistentEntityShouldRemoveRecord() {
        eventHandler.on(new RemovePresentCommand(PRESENT_ID));
    }

    @Test
    @DataSet("present/present_empty.yml")
    @ExpectedQueryCount(
            queries = {
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.SELECT, count = 1),
                    @ExpectedQueryCount.Query(type = ExpectedQueryCount.Type.DELETE, count = 0)
            })
    void removeNonexistentEntityShouldThrowException() {
        assertThrows(EmptyResultDataAccessException.class, () -> eventHandler.on(new RemovePresentCommand(newUUID())));
    }
}