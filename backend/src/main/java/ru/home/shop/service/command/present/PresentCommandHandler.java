package ru.home.shop.service.command.present;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Present;
import ru.home.shop.domain.PresentItem;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PresentCommandHandler {

    private final PresentRepository repository;

    @Autowired
    public PresentCommandHandler(PresentRepository repository) {
        this.repository = repository;
    }

    public void on(CreatePresentCommand event) {
        Present present = new Present();
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
        item.setCandy(new Candy());
        item.getCandy().setId(apiItem.getCandyId());
        item.setCount(apiItem.getCount());

        return item;
    }

    public void on(RemovePresentCommand event) {
        repository.delete(event.getId());
    }
}
