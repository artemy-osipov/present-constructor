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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class CandyEntryRepositoryIT {

    @Autowired
    private CandyEntryRepository repository;

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

        repository.add(getCandy());

        assertEquals(++before, dsl.fetchCount(CANDY));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addWithEmptyNotNullableFieldShouldThrowException() {
        CandyEntry candy = getCandy();
        candy.setPrice(null);

        repository.add(candy);
    }

    @Test
    @FlywayTest
    public void removeExistentEntityShouldRemoveOneEntry() {
        int updated = repository.remove(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        assertThat(updated, equalTo(1));
    }

    @Test
    public void removeNonexistentEntityShouldRemoveNone() {
        assertEquals(0, repository.remove(newUUID()));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldUpdateOneEntry() {
        CandyEntry candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        assertEquals(1, repository.edit(candy));
    }

    @Test
    @FlywayTest
    public void editNonexistentEntityShouldUpdateNone() {
        CandyEntry candy = getCandy();
        candy.setId(newUUID());

        assertEquals(0, repository.edit(candy));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void editNotValidEntryShouldThrowException() {
        CandyEntry candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        candy.setPrice(null);

        repository.edit(candy);
    }

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.list().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        Optional<CandyEntry> fromDB = repository.findById(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        if (!fromDB.isPresent()) {
            fail();
        }

        CandyEntry candy = fromDB.get();
        assertEquals("someName1", candy.getName());
        assertEquals("someFirm1", candy.getFirm());
        assertEquals(BigDecimal.valueOf(2.5).doubleValue(), candy.getPrice().doubleValue(), 0);
        assertEquals(1.1, candy.getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        Optional<CandyEntry> fromDB = repository.findById(newUUID());
        assertThat(fromDB.isPresent(), equalTo(false));
    }
}