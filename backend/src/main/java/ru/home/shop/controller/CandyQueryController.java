package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.service.query.candy.CandyQuery;
import ru.home.shop.service.query.candy.CandyQueryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/candies")
@RequiredArgsConstructor
public class CandyQueryController {

    private final CandyQueryRepository repository;

    @GetMapping(value = "/{id}")
    public CandyQuery findCandy(@PathVariable("id") UUID id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    public Collection<CandyQuery> listCandy() {
        return repository.list();
    }
}
