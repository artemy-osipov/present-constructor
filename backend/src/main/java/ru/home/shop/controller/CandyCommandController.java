package ru.home.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.RemoveCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.controller.dto.CandyDTO;
import ru.home.shop.service.command.candy.CandyCommandHandler;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/candies")
public class CandyCommandController {

    private final CandyCommandHandler commandHandler;

    @Autowired
    public CandyCommandController(CandyCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public ResponseEntity<?> addCandy(@RequestBody @Validated CandyDTO dto) {
        UUID newId = newUUID();
        CreateCandyCommand command = new CreateCandyCommand(newId, dto.getName(), dto.getFirm(), dto.getOrder(), dto.getPrice());

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .location(fromCurrentRequestUri()
                        .path("/{id}").buildAndExpand(newId).toUri())
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editCandy(
            @PathVariable("id") UUID id,
            @RequestBody @Validated CandyDTO dto) {
        UpdateCandyCommand command = new UpdateCandyCommand(id, dto.getName(), dto.getFirm(), dto.getOrder(), dto.getPrice());

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeCandy(@PathVariable("id") UUID id) {
        RemoveCandyCommand command = new RemoveCandyCommand(id);

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }
}
