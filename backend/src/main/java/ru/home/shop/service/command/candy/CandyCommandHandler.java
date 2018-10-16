package ru.home.shop.service.command.candy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.HideCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.domain.Candy;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandyCommandHandler {

    private final CandyRepository repository;

    public void on(CreateCandyCommand event) {
        Candy candy = new Candy();
        candy.setId(event.getId());
        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.save(candy);
    }

    public void on(UpdateCandyCommand event) {
        Candy candy = repository.findById(event.getId())
                .orElseThrow(EntityNotFoundException::new);

        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());
    }

    public void on(HideCandyCommand event) {
        Candy candy = repository.findById(event.getId())
                .orElseThrow(EntityNotFoundException::new);

        candy.hide();
    }
}
