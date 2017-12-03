package ru.home.shop.query.candy;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.shop.api.candy.CandyCreatedEvent;
import ru.home.shop.api.candy.CandyRemovedEvent;
import ru.home.shop.api.candy.CandyUpdatedEvent;

@Component
public class CandyEventHandler {

    private final CandyEntryRepository repository;

    @Autowired
    public CandyEventHandler(CandyEntryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(CandyCreatedEvent event) {
        CandyEntry candy = new CandyEntry();
        candy.setId(event.getId());
        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.add(candy);
    }

    @EventHandler
    public void on(CandyUpdatedEvent event) {
        CandyEntry candy = new CandyEntry();
        candy.setId(event.getId());
        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.edit(candy);
    }

    @EventHandler
    public void on(CandyRemovedEvent event) {
        repository.remove(event.getId());
    }
}
