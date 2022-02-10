package io.github.artemy.osipov.shop.controller;

import io.github.artemy.osipov.shop.controller.converter.DPresentConverter;
import io.github.artemy.osipov.shop.controller.dto.DAddPresent;
import io.github.artemy.osipov.shop.controller.dto.DPresent;
import io.github.artemy.osipov.shop.service.present.CreatePresentCommand;
import io.github.artemy.osipov.shop.service.present.PresentCommandHandler;
import io.github.artemy.osipov.shop.service.present.PresentRepository;
import io.github.artemy.osipov.shop.service.present.RemovePresentCommand;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID;

@RestController
@RequestMapping("/api/presents")
@RequiredArgsConstructor
public class PresentController {

    private final DPresentConverter converter = Mappers.getMapper(DPresentConverter.class);
    private final PresentRepository repository;
    private final PresentCommandHandler commandHandler;

    @GetMapping(value = "/{id}")
    public DPresent getPresent(@PathVariable("id") UUID id) {
        return converter.toDPresent(
                repository.getById(id)
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
        commandHandler.on(
                new RemovePresentCommand(id)
        );

        return ResponseEntity.noContent()
                .build();
    }
}
