package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.query.candy.CandyEntryRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/candies")
public class CandyQueryController {

    private final CandyEntryRepository repository;

    @Autowired
    public CandyQueryController(CandyEntryRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public CandyEntry findCandy(@PathVariable("id") UUID id) {
        Optional<CandyEntry> candy = repository.findById(id);

        if (candy.isPresent()) {
            return candy.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping
    public Collection<CandyEntry> listCandy() {
        return repository.list();
    }
}
