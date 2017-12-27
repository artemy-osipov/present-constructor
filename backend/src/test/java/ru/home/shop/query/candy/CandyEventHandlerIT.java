package ru.home.shop.query.candy;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.api.candy.CandyCreatedEvent;
import ru.home.shop.api.candy.CandyRemovedEvent;
import ru.home.shop.api.candy.CandyUpdatedEvent;
import ru.home.shop.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class CandyEventHandlerIT {

    @Autowired
    private CandyEventHandler eventHandler;

    @Autowired
    private DSLContext dsl;

    private CandyEntry getCandy() {
        CandyEntry bean = new CandyEntry();
        bean.setId(newUUID());
        bean.setName("name");
        bean.setFirm("firm");
        bean.setPrice(BigDecimal.valueOf(2.6));
        bean.setOrder(0.1);

        return bean;
    }

    @Test
    public void addShouldAddNewCandy() {
        int before = dsl.fetchCount(CANDY);

        eventHandler.on(new CandyCreatedEvent(getCandy()));

        assertEquals(++before, dsl.fetchCount(CANDY));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addWithEmptyNotNullableFieldShouldThrowException() {
        CandyEntry candy = getCandy();
        candy.setPrice(null);

        eventHandler.on(new CandyCreatedEvent(candy));
    }

    @Test
    @FlywayTest
    public void removeExistentEntityShouldRemoveOneEntry() {
        eventHandler.on(new CandyRemovedEvent(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c")));
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNonexistentEntityShouldRemoveNone() {
        eventHandler.on(new CandyRemovedEvent(newUUID()));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldUpdateOneEntry() {
        CandyEntry candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        eventHandler.on(new CandyUpdatedEvent(candy));
    }

    @Test(expected = EntityNotFoundException.class)
    @FlywayTest
    public void editNonexistentEntityShouldUpdateNone() {
        CandyEntry candy = getCandy();
        candy.setId(newUUID());

        eventHandler.on(new CandyUpdatedEvent(candy));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void editNotValidEntryShouldThrowException() {
        CandyEntry candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        candy.setPrice(null);

        eventHandler.on(new CandyUpdatedEvent(candy));
    }
}