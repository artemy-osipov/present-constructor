package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.query.present.PresentEntry;
import ru.home.shop.query.present.PresentEntryQueryRepository;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/presents")
public class PresentQueryController {

    private final PresentEntryQueryRepository repository;

    @Autowired
    public PresentQueryController(PresentEntryQueryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public PresentEntry findPresent(@PathVariable("id") UUID id) {
        PresentEntry present = repository.findById(id);

        if (present != null) {
            return present;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping
    public Collection<PresentEntry> listPresent() {
        return repository.list();
    }
}
