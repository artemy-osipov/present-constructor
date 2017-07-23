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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.home.shop.PresentsApplication;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.home.db.tables.Present.PRESENT;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class TransactionTest {

    @Autowired
    private PresentRepository repository;

    @Autowired
    private DSLContext dsl;

    private Present getPresent() {
        Present bean = new Present();
        bean.setId(Generators.timeBasedGenerator().generate());
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));

        return bean;
    }

    @Test
    @FlywayTest
    public void transactionTest() {
        Present present = getPresent();

        Candy candy = new Candy();
        candy.setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        candy.setCount(2);

        present.getCandies().add(candy);

        int before = dsl.fetchCount(PRESENT);

        repository.add(present);
        assertEquals(++before, dsl.fetchCount(PRESENT));

        try {
            Candy incorrectCandy = new Candy();
            incorrectCandy.setId(Generators.timeBasedGenerator().generate());
            incorrectCandy.setCount(2);
            present.getCandies().add(incorrectCandy);
            repository.add(present);
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }

        assertEquals(before, dsl.fetchCount(PRESENT));
    }
}
