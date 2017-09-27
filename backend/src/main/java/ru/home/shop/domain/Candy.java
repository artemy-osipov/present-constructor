package ru.home.shop.domain;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import ru.home.shop.api.candy.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Candy {

    @AggregateIdentifier
    private UUID id;
    private String name;
    private String firm;
    private double order;
    private BigDecimal price;
    private Boolean active;

    private Candy() {
    }

    public Candy(UUID id, String name, String firm, double order, BigDecimal price, Boolean active) {
        this.id = id;
        this.name = name;
        this.firm = firm;
        this.order = order;
        this.price = price;
        this.active = active;
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
        this.active = true;
        fill(event);
    }

    @EventHandler
    private void on(CandyUpdatedEvent event) {
        fill(event);
    }

    private void fill(AbstractCandyEvent event) {
        this.name = event.getName();
        this.firm = event.getFirm();
        this.order = event.getOrder();
        this.price = event.getPrice();
    }

    @EventHandler
    private void on(CandyRemovedEvent event) {
        this.active = false;
    }
}
