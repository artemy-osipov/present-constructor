package ru.home.shop.domain.repo.jooq;

import com.fasterxml.uuid.Generators;
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
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static ru.home.db.Tables.CANDY;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class JOOQCandyRepositoryIT {

    @Autowired
    private CandyRepository repository;

    @Autowired
    private DSLContext dsl;

    private Candy getCandy() {
        Candy bean = new Candy();
        bean.setId(Generators.timeBasedGenerator().generate());
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
        Candy candy = getCandy();
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
        assertEquals(0, repository.remove(Generators.timeBasedGenerator().generate()));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldUpdateOneEntry() {
        Candy candy = getCandy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        assertEquals(1, repository.edit(candy));
    }

    @Test
    @FlywayTest
    public void editNonexistentEntityShouldUpdateNone() {
        Candy candy = getCandy();
        candy.setId(Generators.timeBasedGenerator().generate());

        assertEquals(0, repository.edit(candy));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void editNotValidEntryShouldThrowException() {
        Candy candy = getCandy();
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
        Candy fromDB = repository.findById(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));

        assertEquals("someName1", fromDB.getName());
        assertEquals("someFirm1", fromDB.getFirm());
        assertEquals(BigDecimal.valueOf(2.5).doubleValue(), fromDB.getPrice().doubleValue(), 0);
        assertEquals(1.1, fromDB.getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        assertNull(repository.findById(Generators.timeBasedGenerator().generate()));
    }
}