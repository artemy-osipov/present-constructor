package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Present;
import ru.home.shop.service.PresentService;

@RestController
public class PresentCommandController {

    private final PresentService presentService;

    @Autowired
    public PresentCommandController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(value = "/present", method = RequestMethod.POST)
    public int addPresent(@RequestBody Present present) {
        presentService.add(present);

        return present.getId();
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.PUT)
    public void editPresent(@PathVariable("id") int id, @RequestBody Present present) {
        present.setId(id);
        presentService.edit(present);
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.DELETE)
    public void removePresent(@PathVariable("id") int id) {
        presentService.remove(id);
    }
}
