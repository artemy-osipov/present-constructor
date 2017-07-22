package ru.home.shop.domain.repo.jooq;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.repo.CandyRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static ru.home.db.Tables.CANDY_HISTORY;
import static ru.home.db.tables.Candy.CANDY;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class JOOQCandyRepositoryTest {

    @Autowired
    private CandyRepository repository;

    @Autowired
    private DSLContext dsl;

    private CandyBean getCandy() {
        CandyBean bean = new CandyBean();
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

    @Test
    public void addShouldAddNewVersionOfCandy() {
        int before = dsl.fetchCount(CANDY_HISTORY);

        repository.add(getCandy());

        assertEquals(++before, dsl.fetchCount(CANDY_HISTORY));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addWithEmptyNotNullableFieldShouldThrowException() {
        CandyBean candy = getCandy();
        candy.setPrice(null);

        repository.add(candy);
    }

    @Test
    @FlywayTest
    public void removeExistentEntityShouldRemoveOneEntry() {
        assertEquals(1, repository.remove(1));
    }

    @Test
    public void removeNonexistentEntityShouldRemoveNone() {
        assertEquals(0, repository.remove(-1));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldUpdateOneEntry() {
        CandyBean candy = getCandy();
        candy.setId(1);

        assertEquals(1, repository.edit(candy));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldAddNewVersion() {
        CandyBean candy = getCandy();
        candy.setId(1);

        int before = dsl.fetchCount(CANDY_HISTORY);

        repository.edit(candy);

        assertEquals(++before, dsl.fetchCount(CANDY_HISTORY));
    }

    @Test
    @FlywayTest
    public void editNonexistentEntityShouldUpdateNone() {
        CandyBean candy = getCandy();
        candy.setId(-1);

        assertEquals(0, repository.edit(candy));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void editNotValidEntryShouldThrowException() {
        CandyBean candy = getCandy();
        candy.setId(1);
        candy.setPrice(null);

        repository.edit(candy);
    }

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.findAll().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        CandyBean fromDB = repository.find(1);

        assertEquals("someName1", fromDB.getName());
        assertEquals("someFirm1", fromDB.getFirm());
        assertEquals(BigDecimal.valueOf(2.5).doubleValue(), fromDB.getPrice().doubleValue(), 0);
        assertEquals(1.1, fromDB.getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        assertNull(repository.find(-1));
    }
}