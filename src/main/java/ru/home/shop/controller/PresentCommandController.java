package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.model.Present;
import ru.home.shop.service.PresentService;

import java.util.UUID;

@RestController
@RequestMapping("/presents")
public class PresentCommandController {

    private final PresentService presentService;

    @Autowired
    public PresentCommandController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UUID> addPresent(@RequestBody Present present) {
        presentService.add(present);

        return new ResponseEntity<>(present.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editPresent(@PathVariable("id") UUID id, @RequestBody Present present) {
        present.setId(id);
        presentService.edit(present);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removePresent(@PathVariable("id") UUID id) {
        presentService.remove(id);
    }
}
