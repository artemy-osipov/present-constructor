package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.home.shop.domain.model.Present;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.PresentService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/presents")
public class PresentQueryController {

    private final PresentService presentService;

    @Autowired
    public PresentQueryController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Present findPresent(@PathVariable("id") UUID id) {
        Present present = presentService.find(id);

        if (present != null) {
            return present;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Present> listPresent() {
        return presentService.listView();
    }
}
