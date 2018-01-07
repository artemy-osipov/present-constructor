package ru.home.shop.service.command.present;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.PresentItem;
import ru.home.shop.service.DBRiderIT;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static ru.home.db.Tables.CANDY;
import static ru.home.db.Tables.PRESENT;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentCommandHandlerIT extends DBRiderIT {

    @Autowired
    private PresentCommandHandler eventHandler;

    public PresentCommandHandlerIT() {
        cleanDataAfterClass(PRESENT, CANDY);
    }

    private Present getPresent() {
        Present bean = new Present();
        bean.setId(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985"));
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(12.35));
        bean.setDate(LocalDateTime.of(2017, 1, 1, 12, 0));
        bean.setItems(new ArrayList<>());

        PresentItem item1 = new PresentItem();
        item1.setId(UUID.fromString("a58051ef-ea6c-4565-aefd-0e2d260bf95d"));
        item1.setCandy(new Candy());
        item1.getCandy().setId(UUID.fromString("a764c765-483c-492b-ac63-4f2c4f6d2ff4"));
        item1.setCount(2);

        PresentItem item2 = new PresentItem();
        item2.setId(UUID.fromString("f93944ca-ab01-4f68-b635-6e239efdbb4f"));
        item2.setCandy(new Candy());
        item2.getCandy().setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        item2.setCount(6);

        bean.getItems().add(item1);
        bean.getItems().add(item2);

        return bean;
    }

    @Test
    @DataSet({"present/present_empty.yml", "candy/candy_list.yml"})
    @ExpectedDataSet("present/present.yml")
    public void addValidPresent() {
        eventHandler.on(new CreatePresentCommand(getPresent()));
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    @ExpectedDataSet("present/present_empty.yml")
    public void removeExistentEntityShouldRemoveOneEntry() {
        eventHandler.on(new RemovePresentCommand(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985")));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @DataSet("present/present_empty.yml")
    public void removeNonexistentEntityShouldThrowException() {
        eventHandler.on(new RemovePresentCommand(newUUID()));
    }
}