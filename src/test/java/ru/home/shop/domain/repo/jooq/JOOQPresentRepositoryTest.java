package ru.home.shop.domain.repo.jooq;

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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static ru.home.db.Tables.PRESENT;
import static ru.home.db.Tables.PRESENT_ITEM;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class JOOQPresentRepositoryTest {

    @Autowired
    private PresentRepository repository;

    @Autowired
    private DSLContext dsl;

    private Present getPresent() {
        Present bean = new Present();
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));

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
        Present present = getPresent();

        Candy candy = new Candy();
        candy.setId(1);
        candy.setCount(2);

        present.getCandies().add(candy);

        int presentID = repository.add(present);

        Select count = dsl.selectCount()
                .from(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT.eq(presentID));
        assertEquals(1, dsl.fetchCount(count));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_emptyPrice_shouldThrowException() {
        Present present = getPresent();
        present.setPrice(null);

        repository.add(present);
    }

    @Test
    @FlywayTest
    public void removeByExistentIdShouldRemoveOneEntry() {
        assertEquals(1, repository.remove(1));
    }

    @Test
    public void removeByNonexistentIdShouldRemoveNoneEntry() {
        assertEquals(0, repository.remove(-1));
    }

    @Test
    @FlywayTest
    public void editValidEntryShouldUpdateOneEntry() {
        Present present = getPresent();
        present.setId(1);

        assertEquals(1, repository.edit(present));
    }

    @Test
    @FlywayTest
    public void editByNonexistentIdShouldUpdateNoneEntry() {
        Present present = getPresent();
        present.setId(-1);

        assertEquals(0, repository.edit(present));
    }

    @Test(expected = DataIntegrityViolationException.class)
    @FlywayTest
    public void editNotValidEntryShouldThrowException() {
        Present present = getPresent();
        present.setId(1);
        present.setPrice(null);

        repository.edit(present);
    }

    @Test
    @FlywayTest
    public void editWithCandiesShouldUpdateCandies() {
        Present present = getPresent();
        present.setId(1);

        Candy candy = new Candy();
        candy.setId(1);
        candy.setCount(2);

        present.getCandies().add(candy);

        int presentID = repository.add(present);
        Select count = dsl.selectCount()
                .from(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT.eq(presentID));

        assertEquals(1, dsl.fetchCount(count));
    }

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.findAll().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        Present fromDB = repository.findFull(1);

        assertEquals("someName", fromDB.getName());
        assertEquals(BigDecimal.valueOf(12.35).doubleValue(), fromDB.getPrice().doubleValue(), 0);

        assertEquals(2, fromDB.getCandies().size());

        Candy candy1FromDB = fromDB.getCandies().iterator().next();
        assertEquals(6, candy1FromDB.getCount());

        assertEquals(Integer.valueOf(3), candy1FromDB.getId());
        assertEquals("someName3", candy1FromDB.getName());
        assertEquals("someFirm3", candy1FromDB.getFirm());
        assertEquals(BigDecimal.valueOf(13213.11), candy1FromDB.getPrice());
        assertEquals(3, candy1FromDB.getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        assertNull(repository.findFull(-1));
    }
}