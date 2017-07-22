package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.service.CandyService;

@RestController
public class CandyCommandController {

    private final CandyService candyService;

    @Autowired
    public CandyCommandController(CandyService candyService) {
        this.candyService = candyService;
    }

    @RequestMapping(value = "/candy", method = RequestMethod.POST)
    public int addCandy(@RequestBody Candy candy) {
        candyService.add(candy);

        return candy.getId();
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.PUT)
    public void editCandy(@PathVariable("id") int id, @RequestBody Candy candy) {
        candy.setId(id);
        candyService.edit(candy);
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.DELETE)
    public void removeCandy(@PathVariable("id") int id) {
        candyService.remove(id);
    }
}
