package ru.home.shop.controller;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.home.shop.controller.converter.DPresentConverter;
import ru.home.shop.controller.dto.DPresent;
import ru.home.shop.exception.EntityNotFoundException;
import ru.home.shop.service.present.CreatePresentCommand;
import ru.home.shop.service.present.CreatePresentCommand.PresentItem;
import ru.home.shop.service.present.PresentRepository;
import ru.home.shop.service.present.RemovePresentCommand;
import ru.home.shop.controller.dto.DAddPresent;
import ru.home.shop.service.present.PresentCommandHandler;
import ru.home.shop.service.present.PresentModel;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.home.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/presents")
@RequiredArgsConstructor
public class PresentController {

    private final DPresentConverter converter = Mappers.getMapper(DPresentConverter.class);
    private final PresentRepository repository;
    private final PresentCommandHandler commandHandler;

    @GetMapping(value = "/{id}")
    public DPresent findPresent(@PathVariable("id") UUID id) {
        return repository.findById(id)
                .map(converter::toDPresent)
                .orElseThrow(() ->
                        new EntityNotFoundException(PresentModel.class, id)
                );
    }

    @GetMapping
    public Collection<DPresent> listPresent() {
        return repository.findAll()
                .stream()
                .map(converter::toDPresent)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<UUID> addPresent(@RequestBody @Validated DAddPresent dto) {
        CreatePresentCommand command = converter.toCreateCommand(newUUID(), dto);

        commandHandler.on(command);

        return ResponseEntity
                .ok(command.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removePresent(@PathVariable("id") UUID id) {
        RemovePresentCommand command = new RemovePresentCommand(id);

        commandHandler.on(command);

        return ResponseEntity.noContent()
                .build();
    }
}
