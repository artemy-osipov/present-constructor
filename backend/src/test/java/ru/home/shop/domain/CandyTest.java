package ru.home.shop.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;
import ru.home.shop.api.candy.*;

import java.math.BigDecimal;

import static ru.home.shop.utils.UuidUtils.newUUID;

public class CandyTest {

    private FixtureConfiguration<Candy> fixture = new AggregateTestFixture<>(Candy.class);
    private CreateCandyCommand initCreateCommand = new CreateCandyCommand(
            newUUID(),
            "name",
            "firm",
            1.1,
            BigDecimal.valueOf(123.12));

    @Test
    public void testCreateCandyCommand() {
        fixture.given()
                .when(initCreateCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyCreatedEvent(
                        initCreateCommand.getCandyId(),
                        initCreateCommand.getName(),
                        initCreateCommand.getFirm(),
                        initCreateCommand.getOrder(),
                        initCreateCommand.getPrice()));
    }

    @Test
    public void testUpdateCandyCommand() {
        UpdateCandyCommand updateCommand = new UpdateCandyCommand(
                initCreateCommand.getCandyId(),
                "name2",
                "firm2",
                2.2,
                BigDecimal.valueOf(124.12));

        fixture.givenCommands(initCreateCommand)
                .when(updateCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyUpdatedEvent(
                        initCreateCommand.getCandyId(),
                        updateCommand.getName(),
                        updateCommand.getFirm(),
                        updateCommand.getOrder(),
                        updateCommand.getPrice()));
    }

    @Test
    public void testRemoveCandyCommand() {
        fixture.givenCommands(initCreateCommand)
                .when(new RemoveCandyCommand(initCreateCommand.getCandyId()))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyRemovedEvent(initCreateCommand.getCandyId()));
    }
}