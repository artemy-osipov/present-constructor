package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.service.CandyService;

import java.util.UUID;

@RestController
public class CandyCommandController {

    private final CandyService candyService;

    @Autowired
    public CandyCommandController(CandyService candyService) {
        this.candyService = candyService;
    }

    @RequestMapping(value = "/candy", method = RequestMethod.POST)
    public UUID addCandy(@RequestBody Candy candy) {
        candyService.add(candy);

        return candy.getId();
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.PUT)
    public void editCandy(@PathVariable("id") String id, @RequestBody Candy candy) {
        candy.setId(UUID.fromString(id));
        candyService.edit(candy);
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.DELETE)
    public void removeCandy(@PathVariable("id") String id) {
        candyService.remove(UUID.fromString(id));
    }
}
