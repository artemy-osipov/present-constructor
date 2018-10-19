package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.CreatePresentCommand.PresentItem;
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
@RequiredArgsConstructor
public class PresentCommandController {

    private final PresentCommandHandler commandHandler;

    @PostMapping
    public ResponseEntity<Void> addPresent(@RequestBody @Validated AddPresentDTO dto) {
        CreatePresentCommand command = new CreatePresentCommand(
                newUUID(),
                dto.getName(),
                dto.getPrice(),
                LocalDateTime.now(),
                dto.getItems().stream()
                        .map(i -> new PresentItem(i.getCandy().getId(), i.getCount()))
                        .collect(Collectors.toList())
        );

        commandHandler.on(command);

        return ResponseEntity
                .created(fromCurrentRequestUri()
                        .path("/{id}").buildAndExpand(command.getId()).toUri())
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removePresent(@PathVariable("id") UUID id) {
        RemovePresentCommand command = new RemovePresentCommand(id);

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }
}
