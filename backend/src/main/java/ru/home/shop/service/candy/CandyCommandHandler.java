package ru.home.shop.service.candy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.shop.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CandyCommandHandler {

    private final CandyRepository repository;

    public void on(CreateCandyCommand event) {
        CandyModel candy = new CandyModel();
        candy.setId(event.getId());
        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());

        repository.save(candy);
    }

    public void on(UpdateCandyCommand event) {
        CandyModel candy = repository.findById(event.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(CandyModel.class, event.getId())
                );

        candy.setName(event.getName());
        candy.setFirm(event.getFirm());
        candy.setPrice(event.getPrice());
        candy.setOrder(event.getOrder());
    }

    public void on(HideCandyCommand event) {
        CandyModel candy = repository.findById(event.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(CandyModel.class, event.getId())
                );

        candy.hide();
    }
}
