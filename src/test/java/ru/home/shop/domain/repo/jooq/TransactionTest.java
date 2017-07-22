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
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.repo.PresentRepository;

import java.math.BigDecimal;

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

    private PresentBean getPresent() {
        PresentBean bean = new PresentBean();
        bean.setName("name");
        bean.setPrice(BigDecimal.valueOf(2.6));

        return bean;
    }

    @Test
    @FlywayTest
    public void transactionTest() {
        PresentBean present = getPresent();

        CandyBean candy = new CandyBean();
        candy.setVid(1);
        candy.setCount(2);

        present.getCandies().add(candy);

        int before = dsl.fetchCount(PRESENT);

        repository.add(present);
        assertEquals(++before, dsl.fetchCount(PRESENT));

        try {
            CandyBean incorrectCandy = new CandyBean();
            incorrectCandy.setVid(-11);
            incorrectCandy.setCount(2);
            present.getCandies().add(incorrectCandy);
            repository.add(present);
            fail();
        } catch (DataIntegrityViolationException ignored) {
        }

        assertEquals(before, dsl.fetchCount(PRESENT));
    }
}
