package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.api.candy.CreateCandyCommand;
import ru.home.shop.api.candy.HideCandyCommand;
import ru.home.shop.api.candy.UpdateCandyCommand;
import ru.home.shop.controller.dto.CandyDTO;
import ru.home.shop.service.command.candy.CandyCommandHandler;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/candies")
@RequiredArgsConstructor
public class CandyCommandController {

    private final CandyCommandHandler commandHandler;

    @PostMapping
    public ResponseEntity<Void> addCandy(@RequestBody @Validated CandyDTO dto) {
        CreateCandyCommand command = CreateCandyCommand.builder()
                .id(newUUID())
                .name(dto.getName())
                .firm(dto.getFirm())
                .price(dto.getPrice())
                .order(dto.getOrder())
                .build();

        commandHandler.on(command);

        return ResponseEntity
                .created(fromCurrentRequestUri()
                        .path("/{id}").buildAndExpand(command.getId()).toUri())
                .build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> editCandy(
            @PathVariable("id") UUID id,
            @RequestBody @Validated CandyDTO dto) {
        UpdateCandyCommand command = UpdateCandyCommand.builder()
                .id(id)
                .name(dto.getName())
                .firm(dto.getFirm())
                .price(dto.getPrice())
                .order(dto.getOrder())
                .build();

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeCandy(@PathVariable("id") UUID id) {
        HideCandyCommand command = new HideCandyCommand(id);

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }
}
