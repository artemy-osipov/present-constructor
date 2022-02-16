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
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/presents")
class PresentController(
    private val repository: PresentRepository,
    private val commandHandler: PresentCommandHandler
) {
    private val converter = Mappers.getMapper(DPresentConverter::class.java)

    @GetMapping("/{id}")
    fun getPresent(@PathVariable("id") id: UUID): Mono<DPresent> {
        return repository.getById(id)
            .map(converter::toDPresent)
    }

    @GetMapping
    fun listPresent(): Flux<DPresent> {
        return repository.findAll()
            .map(converter::toDPresent)
    }

    @PostMapping
    suspend fun addPresent(@RequestBody @Validated dto: DAddPresent): UUID {
        val command = converter.toCreateCommand(newUUID(), dto)
        commandHandler.on(command)
        return command.id
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun removePresent(@PathVariable("id") id: UUID) {
        commandHandler.on(
            RemovePresentCommand(id)
        )
    }
}