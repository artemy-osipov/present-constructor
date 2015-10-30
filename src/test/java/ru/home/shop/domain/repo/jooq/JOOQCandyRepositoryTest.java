package ru.home.shop.domain.repo.jooq;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.repo.CandyRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static ru.home.db.tables.Candy.CANDY;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class JOOQCandyRepositoryTest {

    @Autowired
    CandyRepository repository;

    @Autowired
    DSLContext dsl;

    private CandyBean getCandy() {
        CandyBean bean = new CandyBean();
        bean.setName("name");
        bean.setFirm("firm");
        bean.setPrice(BigDecimal.valueOf(2.6));
        bean.setOrder(0.1);

        return bean;
    }

    @Test
    public void add_shouldAddNewEntry() {
        int before = dsl.fetchCount(CANDY);

        repository.add(getCandy());

        assertEquals(++before, dsl.fetchCount(CANDY));
    }

    @Test
    public void add_emptyPrice_shouldThrowException() {
        CandyBean candy = getCandy();
        candy.setPrice(null);

        try {
            repository.add(candy);
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }
    }

    @Test
    @FlywayTest
    public void remove_existentId_shouldRemoveOneEntry() {
        assertEquals(1, repository.remove(1));
    }

    @Test
    public void remove_nonexistentId_shouldRemoveNoneEntry() {
        assertEquals(0, repository.remove(-1));
    }

    @Test
    @FlywayTest
    public void edit_validEntry_shouldUpdateOneEntry() {
        CandyBean candy = getCandy();
        candy.setId(1);

        assertEquals(1, repository.edit(candy));
    }

    @Test
    @FlywayTest
    public void edit_nonexistentId_shouldUpdateNoneEntry() {
        CandyBean candy = getCandy();
        candy.setId(-1);

        assertEquals(0, repository.edit(candy));
    }

    @Test
    @FlywayTest
    public void edit_notValidEntry_shouldThrowException() {
        CandyBean candy = getCandy();
        candy.setId(1);
        candy.setPrice(null);

        try {
            repository.edit(candy);
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }
    }

    @Test
    @FlywayTest
    public void findAll_shouldNotReturnEmptySet() {
        assertFalse(repository.findAll().isEmpty());
    }

    @Test
    @FlywayTest
    public void find_existentId_shouldReturnValidEntry() {
        CandyBean fromDB = repository.find(1);

        assertEquals("someName1", fromDB.getName());
        assertEquals("someFirm1", fromDB.getFirm());
        assertEquals(BigDecimal.valueOf(2.5).doubleValue(), fromDB.getPrice().doubleValue(), 0);
        assertEquals(1.1, fromDB.getOrder(), 0.001);
    }

    @Test
    public void find_nonexistentId_shouldReturnNull() {
        assertNull(repository.find(-1));
    }
}