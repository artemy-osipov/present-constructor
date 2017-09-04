package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.controller.dto.PresentItemDTO;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.service.PresentService;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/presents")
public class PresentCommandController {

    private final PresentService presentService;

    @Autowired
    public PresentCommandController(PresentService presentService) {
        this.presentService = presentService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Present> addPresent(@RequestBody @Validated AddPresentDTO dto) {
        Present present = map(dto);
        present.setId(newUUID());
        present.setDate(LocalDateTime.now());
        presentService.add(present);

        return new ResponseEntity<>(present, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removePresent(@PathVariable("id") UUID id) {
        presentService.remove(id);
    }

    private Present map(AddPresentDTO dto) {
        Present present = new Present();
        present.setName(dto.getName());
        present.setPrice(dto.getPrice());

        present.setItems(dto.getItems().stream()
                .map(this::map)
                .collect(Collectors.toList()));

        return present;
    }

    private Candy map(PresentItemDTO dto) {
        Candy candy = new Candy();
        candy.setId(dto.getCandyId());
        candy.setCount(dto.getCount());

        return candy;
    }
}
