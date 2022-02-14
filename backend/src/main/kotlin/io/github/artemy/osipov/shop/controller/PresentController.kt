package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.controller.converter.DPresentConverter
import io.github.artemy.osipov.shop.controller.dto.DAddPresent
import io.github.artemy.osipov.shop.controller.dto.DPresent
import io.github.artemy.osipov.shop.service.present.PresentCommandHandler
import io.github.artemy.osipov.shop.service.present.PresentRepository
import io.github.artemy.osipov.shop.service.present.PresentRepository.Companion.getById
import io.github.artemy.osipov.shop.service.present.RemovePresentCommand
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.mapstruct.factory.Mappers
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/presents")
class PresentController(
    private val repository: PresentRepository,
    private val commandHandler: PresentCommandHandler
) {
    private val converter = Mappers.getMapper(DPresentConverter::class.java)

    @GetMapping("/{id}")
    fun getPresent(@PathVariable("id") id: UUID): DPresent {
        return converter.toDPresent(
            repository.getById(id)
        )
    }

    @GetMapping
    fun listPresent(): Collection<DPresent> {
        return repository.findAll()
            .stream()
            .map(converter::toDPresent)
            .collect(Collectors.toList())
    }

    @PostMapping
    fun addPresent(@RequestBody @Validated dto: DAddPresent): ResponseEntity<UUID> {
        val command = converter.toCreateCommand(newUUID(), dto)
        commandHandler.on(command)
        return ResponseEntity
            .ok(command.id)
    }

    @DeleteMapping("/{id}")
    fun removePresent(@PathVariable("id") id: UUID): ResponseEntity<Void> {
        commandHandler.on(
            RemovePresentCommand(id)
        )
        return ResponseEntity.noContent()
            .build()
    }
}