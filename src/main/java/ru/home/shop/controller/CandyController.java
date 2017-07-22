package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.CandyService;

import java.util.Collection;

@RestController
public class CandyController {

    private final CandyService candyService;

    @Autowired
    public CandyController(CandyService candyService) {
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

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.GET)
    public Candy findCandy(@PathVariable("id") int id) {
        Candy candy = candyService.find(id);

        if (candy != null) {
            return candy;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @RequestMapping(value = "/candy", method = RequestMethod.GET)
    public Collection<Candy> listCandy() {
        return candyService.list();
    }
}
