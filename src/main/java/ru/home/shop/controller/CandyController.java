package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.service.CandyService;

import java.util.Collection;

@RestController
public class CandyController {

    private CandyService candyService;

    @Autowired
    public CandyController(CandyService candyService) {
        this.candyService = candyService;
    }

    @RequestMapping(value = "/candy", method = RequestMethod.PUT)
    public int addCandy(@RequestBody CandyBean candy) {
        candyService.add(candy);

        return candy.getId();
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.POST)
    public void editCandy(@PathVariable("id") int id, @RequestBody CandyBean candy) {
        candy.setId(id);
        candyService.edit(candy);
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.DELETE)
    public void removeCandy(@PathVariable("id") int id) {
        candyService.remove(id);
    }

    @RequestMapping(value = "/candy/{id}", method = RequestMethod.GET)
    public CandyBean findCandy(@PathVariable("id") int id) {
        CandyBean candy = candyService.find(id);

        if (candy != null) {
            return candy;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/candy", method = RequestMethod.GET)
    public Collection<CandyBean> listCandy() {
        return candyService.list();
    }
}
