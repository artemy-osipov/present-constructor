package ru.home.shop.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import ru.home.shop.api.candy.*;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@Aggregate
public class Candy {

    @AggregateIdentifier
    private UUID id;

    private Candy() {
    }

    public Candy(UUID id) {
        this.id = id;
    }

    @CommandHandler
    public Candy(CreateCandyCommand command) {
        apply(new CandyCreatedEvent(command.getCandyId(), command.getName(), command.getFirm(), command.getOrder(), command.getPrice()));
    }

    @CommandHandler
    public void update(UpdateCandyCommand command) {
        apply(new CandyUpdatedEvent(id, command.getName(), command.getFirm(), command.getOrder(), command.getPrice()));
    }

    @CommandHandler
    public void remove(RemoveCandyCommand command) {
        apply(new CandyRemovedEvent(id));
    }

    @EventHandler
    private void on(CandyCreatedEvent event) {
        this.id = event.getId();
    }

    @EventHandler
    private void on(CandyRemovedEvent event) {
        markDeleted();
    }
}
