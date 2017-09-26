package ru.home.shop.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.controller.dto.UpdateCandyDTO;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.Future;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/candies")
public class CandyCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public CandyCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public Future<ResponseEntity<?>> addCandy(@RequestBody @Validated UpdateCandyDTO dto) {
        UUID newId = newUUID();
        URI location = fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(newId).toUri();

        CreateCandyCommand command = new CreateCandyCommand(newId, dto.getName(), dto.getFirm(), dto.getOrder(), dto.getPrice());

        return commandGateway.send(command)
                .thenApply(o -> ResponseEntity
                        .created(location)
                        .build());
    }

    @PutMapping(value = "/{id}")
    public Future<ResponseEntity<?>> editCandy(@PathVariable("id") UUID id, @RequestBody @Validated UpdateCandyDTO dto) {
        UpdateCandyCommand command = new UpdateCandyCommand(id, dto.getName(), dto.getFirm(), dto.getOrder(), dto.getPrice());

        return commandGateway.send(command)
                .thenApply(o -> ResponseEntity
                        .ok()
                        .build());
    }

    @DeleteMapping(value = "/{id}")
    public Future<ResponseEntity<?>> removeCandy(@PathVariable("id") UUID id) {
        RemoveCandyCommand command = new RemoveCandyCommand(id);

        return commandGateway.send(command)
                .thenApply(o -> ResponseEntity
                        .ok()
                        .build());
    }
}
