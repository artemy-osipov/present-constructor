package ru.home.shop.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.PresentCreatedEvent;
import ru.home.shop.api.present.PresentRemovedEvent;
import ru.home.shop.api.present.RemovePresentCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Present {

    @AggregateIdentifier
    private UUID id;
    private String name;
    private BigDecimal price;
    private LocalDateTime date;
    private List<PresentItem> items;

    private Present() {
    }

    public Present(UUID id, String name, BigDecimal price, LocalDateTime date, List<PresentItem> items) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.items = items;
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
        this.name = event.getName();
        this.price = event.getPrice();
        this.date = event.getDate();
        this.items = event.getItems().stream()
                .map(i -> new PresentItem(i.getCandyId(), i.getCount()))
                .collect(Collectors.toList());
    }
}
