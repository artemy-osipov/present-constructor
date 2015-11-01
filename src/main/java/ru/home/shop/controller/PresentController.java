package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.exception.ResourceNotFoundException;
import ru.home.shop.service.PresentService;

import java.util.Collection;

@RestController
public class PresentController {

    private final PresentService presentService;

    @Autowired
    public PresentController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(value = "/present", method = RequestMethod.PUT)
    public int addPresent(@RequestBody PresentBean present) {
        presentService.add(present);

        return present.getId();
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.POST)
    public void editPresent(@PathVariable("id") int id, @RequestBody PresentBean present) {
        present.setId(id);
        presentService.edit(present);
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.DELETE)
    public void removePresent(@PathVariable("id") int id) {
        presentService.remove(id);
    }

    @RequestMapping(value = "/present/{id}", method = RequestMethod.GET)
    public PresentBean findPresent(@PathVariable("id") int id) {
        PresentBean present = presentService.find(id);

        if (present != null) {
            return present;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/present", method = RequestMethod.GET)
    public Collection<PresentBean> listPresent() {
        return presentService.listView();
    }
}
