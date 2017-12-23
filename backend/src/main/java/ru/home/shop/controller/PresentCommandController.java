package ru.home.shop.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.present.CreatePresentCommand;
import ru.home.shop.api.present.PresentItem;
import ru.home.shop.api.present.RemovePresentCommand;
import ru.home.shop.controller.dto.AddPresentDTO;
import ru.home.shop.domain.Present;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/presents")
public class PresentCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public PresentCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public Future<ResponseEntity<Present>> addPresent(@RequestBody @Validated AddPresentDTO dto) {
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

        return commandGateway.send(command)
                .thenApply(o -> noContent()
                        .location(fromCurrentRequestUri()
                        .path("/{id}").buildAndExpand(newId).toUri())
                        .build());
    }

    @DeleteMapping(value = "/{id}")
    public Future<ResponseEntity<?>> removePresent(@PathVariable("id") UUID id) {
        RemovePresentCommand command = new RemovePresentCommand(id);

        return commandGateway.send(command)
                .thenApply(o -> noContent().build());
    }
}
