package ru.home.shop.service.command.present;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.CreatePresentCommand.PresentItem;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.domain.Item;
import ru.home.shop.domain.Present;
import ru.home.shop.service.command.candy.CandyRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PresentCommandHandler {

    private final PresentRepository presentRepository;
    private final CandyRepository candyRepository;

    public void on(CreatePresentCommand event) {
        Present present = new Present();
        present.setId(event.getId());
        present.setName(event.getName());
        present.setPrice(event.getPrice());
        present.setDate(event.getDate());

        for (PresentItem item : event.getItems()) {
            Candy candy = candyRepository.getOne(item.getCandyId());
            present.getItems().add(new Item(candy, item.getCount()));
        }

        presentRepository.save(present);
    }

    public void on(RemovePresentCommand event) {
        presentRepository.deleteById(event.getId());
    }
}
