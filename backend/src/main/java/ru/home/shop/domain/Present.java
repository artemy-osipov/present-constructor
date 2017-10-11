package ru.home.shop.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.PresentCreatedEvent;
import ru.home.shop.api.present.PresentRemovedEvent;
import ru.home.shop.api.present.RemovePresentCommand;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

@Aggregate
public class Present {

    @AggregateIdentifier
    private UUID id;

    private Present() {
    }

    public Present(UUID id) {
        this.id = id;
    }

    @CommandHandler
    public Present(CreatePresentCommand command) {
        apply(new PresentCreatedEvent(command.getPresentId(), command.getName(), command.getPrice(), LocalDateTime.now(), command.getItems()));
    }

    @CommandHandler
    public void remove(RemovePresentCommand command) {
        apply(new PresentRemovedEvent(id));
    }

    @EventHandler
    private void on(PresentCreatedEvent event) {
        this.id = event.getId();
    }

    @EventHandler
    private void on(PresentRemovedEvent event) {
        markDeleted();
    }
}
