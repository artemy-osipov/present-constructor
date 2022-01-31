package ru.home.shop.controller.converter;

import org.mapstruct.Mapper;
import ru.home.shop.config.MapStructConfig;
import ru.home.shop.controller.dto.DCandy;
import ru.home.shop.controller.dto.DEditCandy;
import ru.home.shop.service.candy.CandyModel;
import ru.home.shop.service.candy.CreateCandyCommand;
import ru.home.shop.service.candy.UpdateCandyCommand;

import java.util.UUID;

@Mapper(config = MapStructConfig.class)
public interface DCandyConverter {

    DCandy toDCandy(CandyModel candy);

    CreateCandyCommand toCreateCommand(UUID id, DEditCandy candy);

    UpdateCandyCommand toUpdateCommand(UUID id, DEditCandy candy);
}
