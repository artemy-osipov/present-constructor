package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.query.candy.CandyEntry;
import ru.home.shop.service.CandyService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/candies")
public class CandyQueryController {

    private final CandyService candyService;

    @Autowired
    public CandyQueryController(CandyService candyService) {
        this.candyService = candyService;
    }

    @GetMapping(value = "/{id}")
    public CandyEntry findCandy(@PathVariable("id") UUID id) {
        CandyEntry candy = candyService.find(id);

        if (candy != null) {
            return candy;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @GetMapping
    public Collection<CandyEntry> listCandy() {
        return candyService.list();
    }
}
