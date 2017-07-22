package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.CandyService;

import java.util.Collection;

@RestController
public class CandyQueryController {

    private final CandyService candyService;

    @Autowired
    public CandyQueryController(CandyService candyService) {
        this.candyService = candyService;
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
