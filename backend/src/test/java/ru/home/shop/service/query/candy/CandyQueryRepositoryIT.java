package ru.home.shop.service.query.candy;

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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class CandyQueryRepositoryIT {

    @Autowired
    private CandyQueryRepository repository;

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertThat(repository.list().isEmpty(), equalTo(false));
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        CandyQuery candy = repository.findById(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        assertThat(candy.getName(), equalTo("someName1"));
        assertThat(candy.getFirm(), equalTo("someFirm1"));
        assertThat(candy.getPrice(), equalTo(new BigDecimal("2.50")));
        assertThat(candy.getOrder(), equalTo(1.1));
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        CandyQuery candy = repository.findById(newUUID());
        assertThat(candy, nullValue());
    }
}