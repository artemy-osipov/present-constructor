package ru.home.shop.query.candy;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.shop.api.candy.CandyCreatedEvent;
import ru.home.shop.api.candy.CandyRemovedEvent;
import ru.home.shop.api.candy.CandyUpdatedEvent;
import ru.home.shop.exception.EntityNotFoundException;

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

        repository.save(candy);
    }

    @EventHandler
    public void on(CandyUpdatedEvent event) {
        CandyEntry candy = repository.findOne(event.getId());

        if (candy == null) {
            throw new EntityNotFoundException();
        }

        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.save(candy);
    }

    @EventHandler
    public void on(CandyRemovedEvent event) {
        CandyEntry candy = repository.findOne(event.getId());

        if (candy == null) {
            throw new EntityNotFoundException();
        }

        candy.setActive(false);

        repository.save(candy);
    }
}
