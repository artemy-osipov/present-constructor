package ru.home.shop.domain.repo.jooq;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.jooq.Select;
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
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.repo.PresentRepository;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static ru.home.shop.db.tables.CandyPresent.CANDY_PRESENT;
import static ru.home.shop.db.tables.Present.PRESENT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class JOOQPresentRepositoryTest {

    @Autowired
    PresentRepository repository;

    @Autowired
    DSLContext dsl;

    private PresentBean getPresent() {
        PresentBean bean = new PresentBean();
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));

        return bean;
    }

    @Test
    public void add_shouldAddNewEntry() {
        int before = dsl.fetchCount(PRESENT);

        repository.addFull(getPresent());

        assertEquals(++before, dsl.fetchCount(PRESENT));
    }

    @Test
    public void add_withCandies_shouldAddCandies() {
        PresentBean present = getPresent();

        CandyBean candy = new CandyBean();
        candy.setId(1);
        candy.setCount(2);

        present.getCandies().add(candy);

        int presentID = repository.addFull(present);
        Select count = dsl.selectCount().from(CANDY_PRESENT).where(CANDY_PRESENT.PRESENT.eq(presentID));

        assertEquals(1, dsl.fetchCount(count));
    }

    @Test
    public void add_emptyPrice_shouldThrowException() {
        PresentBean present = getPresent();
        present.setPrice(null);

        try {
            repository.addFull(present);
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
        PresentBean present = getPresent();
        present.setId(1);

        assertEquals(1, repository.editFull(present));
    }

    @Test
    @FlywayTest
    public void edit_nonexistentId_shouldUpdateNoneEntry() {
        PresentBean present = getPresent();
        present.setId(-1);

        assertEquals(0, repository.editFull(present));
    }

    @Test
    @FlywayTest
    public void edit_notValidEntry_shouldThrowException() {
        PresentBean present = getPresent();
        present.setId(1);
        present.setPrice(null);

        try {
            repository.editFull(present);
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }
    }

    @Test
    @FlywayTest
    public void edit_withCandies_shouldUpdateCandies() {
        PresentBean present = getPresent();
        present.setId(1);

        CandyBean candy = new CandyBean();
        candy.setId(1);
        candy.setCount(2);

        present.getCandies().add(candy);

        int presentID = repository.addFull(present);
        Select count = dsl.selectCount().from(CANDY_PRESENT).where(CANDY_PRESENT.PRESENT.eq(presentID));

        assertEquals(1, dsl.fetchCount(count));
    }

    @Test
    @FlywayTest
    public void findAll_shouldNotReturnEmptySet() {
        assertThat(repository.findAll().isEmpty(), is(false));
    }

    @Test
    @FlywayTest
    public void find_existentId_shouldReturnValidEntry() {
        PresentBean fromDB = repository.findFull(1);

        assertEquals("someName", fromDB.getName());
        assertEquals(BigDecimal.valueOf(12.35).doubleValue(), fromDB.getPrice().doubleValue(), 0);

        assertEquals(2, fromDB.getCandies().size());

        CandyBean candy1FromDB = fromDB.getCandies().iterator().next();
        assertEquals(6, candy1FromDB.getCount());

        assertEquals(3, candy1FromDB.getId());
        assertEquals("someName3", candy1FromDB.getName());
        assertEquals("someFirm3", candy1FromDB.getFirm());
        assertEquals(BigDecimal.valueOf(13213.11), candy1FromDB.getPrice());
        assertEquals(3, candy1FromDB.getOrder(), 0.001);
    }

    @Test
    public void find_nonexistentId_shouldReturnNull() {
        assertNull(repository.findFull(-1));
    }
}