package ru.home.shop.query.present;

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

import static org.junit.Assert.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class PresentQueryRepositoryIT {

    @Autowired
    private PresentQueryRepository repository;

    @Test
    @FlywayTest
    public void findAllShouldNotReturnEmptySet() {
        assertFalse(repository.list().isEmpty());
    }

    @Test
    @FlywayTest
    public void findByExistentIdShouldReturnValidEntry() {
        PresentQuery fromDB = repository.findById(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985"));

        assertEquals("someName", fromDB.getName());
        assertEquals(BigDecimal.valueOf(12.35).doubleValue(), fromDB.getPrice().doubleValue(), 0);

        assertEquals(2, fromDB.getItems().size());

        PresentItemQuery item1FromDB = fromDB.getItems().iterator().next();
        assertEquals(6, item1FromDB.getCount().intValue());

        assertEquals(UUID.fromString("b08871d2-cc84-4be0-9671-8c73bf8658ae"), item1FromDB.getCandy().getId());
        assertEquals("someName3", item1FromDB.getCandy().getName());
        assertEquals("someFirm3", item1FromDB.getCandy().getFirm());
        assertEquals(BigDecimal.valueOf(13213.11), item1FromDB.getCandy().getPrice());
        assertEquals(3, item1FromDB.getCandy().getOrder(), 0.001);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        assertNull(repository.findById(newUUID()));
    }
}