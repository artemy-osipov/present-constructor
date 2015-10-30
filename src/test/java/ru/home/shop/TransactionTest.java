package ru.home.shop;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.jooq.DSLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ru.home.db.tables.Candy.CANDY;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {PresentsApplication.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class TransactionTest {

    @Autowired
    DSLContext dsl;

    @Autowired
    HelloService service;

    @Test
    @FlywayTest
    public void transactionTest() {
        int before = dsl.fetchCount(CANDY);

        service.create(5, "someName", "some", 1);
        assertEquals(++before, dsl.fetchCount(CANDY));

        try {
            service.create(6, "someName", "some", 2);
            fail();
        } catch (DuplicateKeyException ignored) {
        }

        assertEquals(before, dsl.fetchCount(CANDY));
    }
}
