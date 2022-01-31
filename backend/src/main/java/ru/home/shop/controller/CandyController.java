package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.controller.converter.DCandyConverter;
import ru.home.shop.controller.dto.DCandy;
import ru.home.shop.service.candy.CreateCandyCommand;
import ru.home.shop.service.candy.HideCandyCommand;
import ru.home.shop.service.candy.UpdateCandyCommand;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.controller.dto.DEditCandy;
import ru.home.shop.service.candy.CandyCommandHandler;
import ru.home.shop.service.candy.CandyModel;
import ru.home.shop.service.candy.CandyRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/candies")
@RequiredArgsConstructor
public class CandyController {

    private final DCandyConverter converter = Mappers.getMapper(DCandyConverter.class);
    private final CandyRepository repository;
    private final CandyCommandHandler commandHandler;

    @GetMapping(value = "/{id}")
    public DCandy findCandy(@PathVariable("id") UUID id) {
        return repository.findById(id)
                .map(converter::toDCandy)
                .orElseThrow(() ->
                        new EntityNotFoundException(CandyModel.class, id)
                );
    }

    @GetMapping
    public Collection<DCandy> listCandy() {
        return repository.findAll()
                .stream()
                .map(converter::toDCandy)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UUID> addCandy(@RequestBody @Validated DEditCandy dto) {
        CreateCandyCommand command = converter.toCreateCommand(newUUID(), dto);

        commandHandler.on(command);

        return ResponseEntity
                .ok(command.getId());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> editCandy(
            @PathVariable("id") UUID id,
            @RequestBody @Validated DEditCandy dto) {
        UpdateCandyCommand command = converter.toUpdateCommand(id, dto);

        commandHandler.on(command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeCandy(@PathVariable("id") UUID id) {
        commandHandler.on(
                new HideCandyCommand(id)
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
