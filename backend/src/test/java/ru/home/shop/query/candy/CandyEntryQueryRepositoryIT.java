package ru.home.shop.query.candy;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class CandyEntryQueryRepositoryIT {

    @Autowired
    private CandyEntryQueryRepository repository;

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.list().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        CandyEntry candy = repository.findById(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        assertEquals("someName1", candy.getName());
        assertEquals("someFirm1", candy.getFirm());
        assertEquals(BigDecimal.valueOf(2.5).doubleValue(), candy.getPrice().doubleValue(), 0);
        assertEquals(1.1, candy.getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        CandyEntry candy = repository.findById(newUUID());
        assertThat(candy, nullValue());
    }
}