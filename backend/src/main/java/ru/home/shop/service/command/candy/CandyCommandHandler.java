package ru.home.shop.service.command.candy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.domain.Candy;
import ru.home.shop.exception.EntityNotFoundException;

import javax.transaction.Transactional;

@Service
@Transactional
public class CandyCommandHandler {

    private final CandyRepository repository;

    @Autowired
    public CandyCommandHandler(CandyRepository repository) {
        this.repository = repository;
    }

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

        repository.save(candy);
    }

    public void on(RemoveCandyCommand event) {
        Candy candy = repository.findById(event.getId())
                .orElseThrow(EntityNotFoundException::new);

        candy.setActive(false);

        repository.save(candy);
    }
}
