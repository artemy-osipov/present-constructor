package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.query.present.PresentQuery;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.query.present.PresentQueryRepository;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/presents")
public class PresentQueryController {

    private final PresentQueryRepository repository;

    @Autowired
    public PresentQueryController(PresentQueryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public PresentQuery findPresent(@PathVariable("id") UUID id) {
        PresentQuery present = repository.findById(id);

        if (present != null) {
            return present;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping
    public Collection<PresentQuery> listPresent() {
        return repository.list();
    }
}
