package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Present;
import ru.home.shop.service.PresentService;

import java.util.UUID;

@RestController
public class PresentCommandController {

    private final PresentService presentService;

    @Autowired
    public PresentCommandController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(value = "/present", method = RequestMethod.POST)
    public UUID addPresent(@RequestBody Present present) {
        presentService.add(present);

        return present.getId();
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.PUT)
    public void editPresent(@PathVariable("id") String id, @RequestBody Present present) {
        present.setId(UUID.fromString(id));
        presentService.edit(present);
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.DELETE)
    public void removePresent(@PathVariable("id") String id) {
        presentService.remove(UUID.fromString(id));
    }
}
