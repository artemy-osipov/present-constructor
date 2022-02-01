package io.github.artemy.osipov.shop.controller;

import io.github.artemy.osipov.shop.controller.converter.DCandyConverter;
import io.github.artemy.osipov.shop.controller.dto.DCandy;
import io.github.artemy.osipov.shop.controller.dto.DEditCandy;
import io.github.artemy.osipov.shop.exception.EntityNotFoundException;
import io.github.artemy.osipov.shop.service.candy.Candy;
import io.github.artemy.osipov.shop.service.candy.CandyCommandHandler;
import io.github.artemy.osipov.shop.service.candy.CandyRepository;
import io.github.artemy.osipov.shop.service.candy.CreateCandyCommand;
import io.github.artemy.osipov.shop.service.candy.HideCandyCommand;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.artemy.osipov.shop.utils.UuidUtils.newUUID;

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
                .orElseThrow(() -> new EntityNotFoundException(Candy.class, id));
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
        commandHandler.on(
                converter.toUpdateCommand(id, dto)
        );

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
