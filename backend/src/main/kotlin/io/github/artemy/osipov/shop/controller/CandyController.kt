package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.controller.converter.DCandyConverter
import io.github.artemy.osipov.shop.controller.dto.DCandy
import io.github.artemy.osipov.shop.controller.dto.DEditCandy
import io.github.artemy.osipov.shop.service.candy.CandyCommandHandler
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.candy.CandyRepository.Companion.getById
import io.github.artemy.osipov.shop.service.candy.HideCandyCommand
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/api/candies")
class CandyController(
    private val repository: CandyRepository,
    private val commandHandler: CandyCommandHandler
) {
    private val converter = Mappers.getMapper(DCandyConverter::class.java)

    @GetMapping("/{id}")
    fun getCandy(@PathVariable("id") id: UUID): Mono<DCandy> {
        return repository.getById(id)
            .map(converter::toDCandy)
    }

    @GetMapping
    fun listCandy(): Flux<DCandy> {
        return repository.findAll()
            .map(converter::toDCandy)
    }

    @PostMapping
    suspend fun addCandy(@RequestBody @Validated dto: DEditCandy): UUID {
        val command = converter.toCreateCommand(newUUID(), dto)
        commandHandler.on(command)
        return command.id
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun editCandy(
        @PathVariable("id") id: UUID,
        @RequestBody @Validated dto: DEditCandy
    ) {
        commandHandler.on(
            converter.toUpdateCommand(id, dto)
        )
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun removeCandy(@PathVariable("id") id: UUID) {
        return commandHandler.on(
            HideCandyCommand(id)
        )
    }
}