package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.controller.converter.DCandyConverter
import io.github.artemy.osipov.shop.controller.dto.DCandy
import io.github.artemy.osipov.shop.controller.dto.DEditCandy
import io.github.artemy.osipov.shop.service.candy.CandyCommandHandler
import io.github.artemy.osipov.shop.service.candy.CandyRepository
import io.github.artemy.osipov.shop.service.candy.CandyRepository.Companion.getById
import io.github.artemy.osipov.shop.service.candy.HideCandyCommand
import io.github.artemy.osipov.shop.utils.UuidUtils.newUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
import java.util.*

@RestController
@RequestMapping("/api/candies")
class CandyController(
    private val repository: CandyRepository,
    private val commandHandler: CandyCommandHandler,
    private val converter: DCandyConverter
) {

    @GetMapping("/{id}")
    suspend fun getCandy(@PathVariable("id") id: UUID): DCandy {
        return converter.toDCandy(
            repository.getById(id)
        )
    }

    @GetMapping
    fun listCandy(): Flow<DCandy> {
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