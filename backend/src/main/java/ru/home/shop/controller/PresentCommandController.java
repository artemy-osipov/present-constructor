package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.PresentItem;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.service.command.present.PresentCommandHandler;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/presents")
public class PresentCommandController {

    private final PresentCommandHandler commandHandler;

    @Autowired
    public PresentCommandController(PresentCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public ResponseEntity<?> addPresent(@RequestBody @Validated AddPresentDTO dto) {
        UUID newId = newUUID();

        CreatePresentCommand command = new CreatePresentCommand(
                newId,
                dto.getName(),
                dto.getPrice(),
                LocalDateTime.now(),
                dto.getItems().stream()
                        .map(i -> new PresentItem(newUUID(), i.getCandy().getId(), i.getCount()))
                        .collect(Collectors.toList())
        );

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .location(fromCurrentRequestUri()
                        .path("/{id}").buildAndExpand(newId).toUri())
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removePresent(@PathVariable("id") UUID id) {
        RemovePresentCommand command = new RemovePresentCommand(id);

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }
}
