package io.github.artemy.osipov.shop.controller.converter

import io.github.artemy.osipov.shop.config.MapStructConfig
import io.github.artemy.osipov.shop.controller.dto.DAddPresent
import io.github.artemy.osipov.shop.controller.dto.DPresent
import io.github.artemy.osipov.shop.controller.dto.DPresentItem
import io.github.artemy.osipov.shop.service.present.CreatePresentCommand
import io.github.artemy.osipov.shop.service.present.Present
import org.mapstruct.Mapper

import java.util.UUID

@Mapper(config = MapStructConfig::class)
interface DPresentConverter {

    fun toDPresent(present: Present): DPresent

    fun toCreateCommand(id: UUID, present: DAddPresent): CreatePresentCommand

    fun toCreateCommandItems(item: DPresentItem): CreatePresentCommand.PresentItem
}
