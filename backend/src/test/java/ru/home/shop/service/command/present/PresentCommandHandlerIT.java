package ru.home.shop.service.command.present;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.PresentItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static ru.home.db.Tables.PRESENT;
import static ru.home.db.Tables.PRESENT_ITEM;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
public class PresentCommandHandlerIT {

    @Autowired
    private PresentCommandHandler eventHandler;

    @Autowired
    private DSLContext dsl;

    private Present getPresent() {
        Present bean = new Present();
        bean.setId(newUUID());
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));
        bean.setDate(LocalDateTime.now());
        bean.setItems(new ArrayList<>());

        return bean;
    }

    @Test
    public void addShouldAddNewEntry() {
        int before = dsl.fetchCount(PRESENT);

        eventHandler.on(new CreatePresentCommand(getPresent()));

        assertEquals(++before, dsl.fetchCount(PRESENT));
    }

    @Test
    public void addWithCandiesShouldAddCandies() {
        Present present = getPresent();
        present.setId(newUUID());

        PresentItem item = new PresentItem();
        item.setId(newUUID());
        item.setCandy(new Candy());
        item.getCandy().setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        item.setCount(2);

        present.getItems().add(item);

        eventHandler.on(new CreatePresentCommand(present));

        Select count = dsl.selectCount()
                .from(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT_ID.eq(present.getId()));
        assertEquals(1, dsl.fetchCount(count));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addInvalidPresentShouldThrowException() {
        Present present = getPresent();
        present.setPrice(null);

        eventHandler.on(new CreatePresentCommand(present));
    }

    @Test
    @FlywayTest
    public void removeByExistentIdShouldRemoveOneEntry() {
        int before = dsl.fetchCount(PRESENT);

        eventHandler.on(new RemovePresentCommand(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985")));

        assertEquals(--before, dsl.fetchCount(PRESENT));
    }
}