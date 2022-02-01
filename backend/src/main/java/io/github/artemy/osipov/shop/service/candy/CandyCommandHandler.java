package io.github.artemy.osipov.shop.service.candy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.artemy.osipov.shop.exception.EntityNotFoundException;

@Service
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
                .orElseThrow(() -> new EntityNotFoundException(Candy.class, event.getId()));

        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.save((candy));
    }

    public void on(HideCandyCommand event) {
        Candy candy = repository.findById(event.getId())
                .orElseThrow(() -> new EntityNotFoundException(Candy.class, event.getId()));

        candy.hide();

        repository.save((candy));
    }
}
