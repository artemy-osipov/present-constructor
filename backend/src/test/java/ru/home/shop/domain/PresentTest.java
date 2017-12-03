package ru.home.shop.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;
import ru.home.shop.api.present.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static ru.home.shop.utils.UuidUtils.newUUID;

public class PresentTest {

    private FixtureConfiguration<Present> fixture = new AggregateTestFixture<>(Present.class);
    private CreatePresentCommand initCreateCommand = new CreatePresentCommand(
            newUUID(),
            "name",
            BigDecimal.valueOf(123.12),
            LocalDateTime.now(),
            Arrays.asList(new PresentItem(newUUID(), 1), new PresentItem(newUUID(), 2)));

    @Test
    public void testCreatePresentCommand() {
        fixture.given()
                .when(initCreateCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new PresentCreatedEvent(
                        initCreateCommand.getPresentId(),
                        initCreateCommand.getName(),
                        initCreateCommand.getPrice(),
                        initCreateCommand.getDate(),
                        initCreateCommand.getItems()));
    }

    @Test
    public void testRemovePresentCommand() {
        fixture.givenCommands(initCreateCommand)
                .when(new RemovePresentCommand(initCreateCommand.getPresentId()))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new PresentRemovedEvent(initCreateCommand.getPresentId()));
    }
}