package ru.home.shop.query.present;

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
import ru.home.shop.query.candy.CandyEntry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;
import static ru.home.db.Tables.PRESENT;
import static ru.home.db.Tables.PRESENT_ITEM;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class PresentEntryRepositoryIT {

    @Autowired
    private PresentEntryRepository repository;

    @Autowired
    private DSLContext dsl;

    private PresentEntry getPresent() {
        PresentEntry bean = new PresentEntry();
        bean.setId(newUUID());
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));
        bean.setDate(LocalDateTime.now());

        return bean;
    }

    @Test
    public void addShouldAddNewEntry() {
        int before = dsl.fetchCount(PRESENT);

        repository.add(getPresent());

        assertEquals(++before, dsl.fetchCount(PRESENT));
    }

    @Test
    public void addWithCandiesShouldAddCandies() {
        PresentEntry present = getPresent();
        present.setId(newUUID());

        PresentItem item = new PresentItem();
        item.setCandy(new CandyEntry());
        item.getCandy().setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        item.setCount(2);

        present.getItems().add(item);

        repository.add(present);

        Select count = dsl.selectCount()
                .from(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT.eq(present.getId()));
        assertEquals(1, dsl.fetchCount(count));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_emptyPrice_shouldThrowException() {
        PresentEntry present = getPresent();
        present.setPrice(null);

        repository.add(present);
    }

    @Test
    @FlywayTest
    public void removeByExistentIdShouldRemoveOneEntry() {
        assertEquals(1, repository.remove(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985")));
    }

    @Test
    public void removeByNonexistentIdShouldRemoveNoneEntry() {
        assertEquals(0, repository.remove(newUUID()));
    }

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.list().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        PresentEntry fromDB = repository.findById(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985"));

        assertEquals("someName", fromDB.getName());
        assertEquals(BigDecimal.valueOf(12.35).doubleValue(), fromDB.getPrice().doubleValue(), 0);

        assertEquals(2, fromDB.getItems().size());

        PresentItem item1FromDB = fromDB.getItems().iterator().next();
        assertEquals(6, item1FromDB.getCount().intValue());

        assertEquals(UUID.fromString("b08871d2-cc84-4be0-9671-8c73bf8658ae"), item1FromDB.getCandy().getId());
        assertEquals("someName3", item1FromDB.getCandy().getName());
        assertEquals("someFirm3", item1FromDB.getCandy().getFirm());
        assertEquals(BigDecimal.valueOf(13213.11), item1FromDB.getCandy().getPrice());
        assertEquals(3, item1FromDB.getCandy().getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        assertNull(repository.findById(newUUID()));
    }
}