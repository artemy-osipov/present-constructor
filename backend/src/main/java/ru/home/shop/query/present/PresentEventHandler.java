package ru.home.shop.query.present;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.shop.api.present.PresentCreatedEvent;
import ru.home.shop.api.present.PresentRemovedEvent;
import ru.home.shop.query.candy.CandyEntry;

import java.util.stream.Collectors;

@Component
public class PresentEventHandler {

    private final PresentEntryRepository repository;

    @Autowired
    public PresentEventHandler(PresentEntryRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PresentCreatedEvent event) {
        PresentEntry present = new PresentEntry();
        present.setId(event.getId());
        present.setName(event.getName());
        present.setPrice(event.getPrice());
        present.setDate(event.getDate());
        present.setItems(
                event.getItems().stream()
                .map(this::map)
                .collect(Collectors.toList())
        );

        repository.save(present);
    }

    private PresentItem map(ru.home.shop.api.present.PresentItem apiItem) {
        PresentItem item = new PresentItem();
        item.setId(apiItem.getId());
        item.setCandy(new CandyEntry());
        item.getCandy().setId(apiItem.getCandyId());
        item.setCount(apiItem.getCount());

        return item;
    }

    @EventHandler
    public void on(PresentRemovedEvent event) {
        repository.delete(event.getId());
    }
}
