package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.controller.dto.PresentItemDTO;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.PresentItem;
import ru.home.shop.service.PresentService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
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

        URI location = fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(present.getId()).toUri();

        return ResponseEntity
                .created(location)
                .build();
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

    private PresentItem map(PresentItemDTO dto) {
        PresentItem item = new PresentItem();
        item.setCandy(new Candy());
        item.getCandy().setId(dto.getCandy().getId());
        item.setCount(dto.getCount());

        return item;
    }
}
