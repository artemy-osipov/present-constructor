package ru.home.shop.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;
import ru.home.shop.api.candy.*;

import java.math.BigDecimal;
import java.util.UUID;

import static ru.home.shop.utils.UuidUtils.newUUID;

public class CandyTest {

    private final FixtureConfiguration<Candy> fixture = new AggregateTestFixture<>(Candy.class);

    @Test
    public void testCreateCandyCommand() {
        UUID newId = newUUID();
        fixture.given()
                .when(new CreateCandyCommand(newId, "name", "firm", 1.1, BigDecimal.valueOf(123.12)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyCreatedEvent(newId, "name", "firm", 1.1, BigDecimal.valueOf(123.12)));
    }

    @Test
    public void testUpdateCandyCommand() {
        CandyCreatedEvent createdEvent = new CandyCreatedEvent(newUUID(), "name", "firm", 1.1, BigDecimal.valueOf(123.12));
        fixture.given(createdEvent)
                .when(new UpdateCandyCommand(createdEvent.getId(), "name2", "firm2", 2.2, BigDecimal.valueOf(124.12)))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyUpdatedEvent(createdEvent.getId(), "name2", "firm2", 2.2, BigDecimal.valueOf(124.12)));
    }

    @Test
    public void testRemoveCandyCommand() {
        CandyCreatedEvent createdEvent = new CandyCreatedEvent(newUUID(), "name", "firm", 1.1, BigDecimal.valueOf(123.12));
        fixture.given(createdEvent)
                .when(new RemoveCandyCommand(createdEvent.getId()))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CandyRemovedEvent(createdEvent.getId()));
    }
}