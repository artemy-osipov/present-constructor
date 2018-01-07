package ru.home.shop.service.command.candy;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.DBRiderIT;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyCommandHandlerIT extends DBRiderIT {

    @Autowired
    private CandyCommandHandler eventHandler;

    public CandyCommandHandlerIT() {
        cleanDataAfterClass(CANDY);
    }

    private Candy getCandy() {
        Candy bean = new Candy();
        bean.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        bean.setName("name");
        bean.setFirm("firm");
        bean.setPrice(BigDecimal.valueOf(2.6));
        bean.setOrder(1.1);

        return bean;
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    @ExpectedDataSet("candy/candy.yml")
    public void addValidCandy() {
        eventHandler.on(new CreateCandyCommand(getCandy()));
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy_not_active.yml")
    public void removeExistentEntityShouldRemoveOneEntry() {
        eventHandler.on(new RemoveCandyCommand(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c")));
    }

    @Test(expected = EntityNotFoundException.class)
    @DataSet("candy/candy_empty.yml")
    public void removeNonexistentEntityShouldRemoveNone() {
        eventHandler.on(new RemoveCandyCommand(newUUID()));
    }

    @Test
    @DataSet("candy/candy.yml")
    @ExpectedDataSet("candy/candy.yml")
    public void editValidEntryShouldUpdateOneEntry() {
        Candy candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        eventHandler.on(new UpdateCandyCommand(candy));
    }

    @Test(expected = EntityNotFoundException.class)
    @DataSet("candy/candy_empty.yml")
    public void editNonexistentEntityShouldUpdateNone() {
        Candy candy = getCandy();
        candy.setId(newUUID());

        eventHandler.on(new UpdateCandyCommand(candy));
    }
}