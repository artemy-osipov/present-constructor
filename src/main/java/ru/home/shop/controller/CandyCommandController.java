package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.controller.dto.UpdateCandyDTO;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.service.CandyService;

import java.util.UUID;

import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/candies")
public class CandyCommandController {

    private final CandyService candyService;

    @Autowired
    public CandyCommandController(CandyService candyService) {
        this.candyService = candyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UUID> addCandy(@RequestBody @Validated UpdateCandyDTO dto) {
        Candy candy = map(dto);
        candy.setId(newUUID());

        candyService.add(candy);

        return new ResponseEntity<>(candy.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editCandy(@PathVariable("id") UUID id, @RequestBody @Validated UpdateCandyDTO dto) {
        Candy candy = map(dto);
        candy.setId(id);
        candyService.edit(candy);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeCandy(@PathVariable("id") UUID id) {
        candyService.remove(id);
    }

    private Candy map(UpdateCandyDTO dto) {
        Candy candy = new Candy();
        candy.setName(dto.getName());
        candy.setFirm(dto.getFirm());
        candy.setPrice(dto.getPrice());
        candy.setOrder(dto.getOrder());

        return candy;
    }
}
