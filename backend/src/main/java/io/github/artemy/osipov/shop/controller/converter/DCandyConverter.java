package io.github.artemy.osipov.shop.controller.converter;

import io.github.artemy.osipov.shop.config.MapStructConfig;
import io.github.artemy.osipov.shop.controller.dto.DCandy;
import io.github.artemy.osipov.shop.controller.dto.DEditCandy;
import io.github.artemy.osipov.shop.service.candy.Candy;
import io.github.artemy.osipov.shop.service.candy.CreateCandyCommand;
import io.github.artemy.osipov.shop.service.candy.UpdateCandyCommand;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(config = MapStructConfig.class)
public interface DCandyConverter {

    DCandy toDCandy(Candy candy);

    CreateCandyCommand toCreateCommand(UUID id, DEditCandy candy);

    UpdateCandyCommand toUpdateCommand(UUID id, DEditCandy candy);
}
