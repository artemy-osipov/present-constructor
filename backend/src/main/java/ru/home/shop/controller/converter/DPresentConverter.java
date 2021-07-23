package ru.home.shop.controller.converter;

import org.mapstruct.Mapper;
import ru.home.shop.config.MapStructConfig;
import ru.home.shop.controller.dto.DAddPresent;
import ru.home.shop.controller.dto.DPresent;
import ru.home.shop.controller.dto.DPresentItem;
import ru.home.shop.service.present.CreatePresentCommand;
import ru.home.shop.service.present.PresentModel;

import java.util.UUID;

@Mapper(config = MapStructConfig.class)
public interface DPresentConverter {

    DPresent toDPresent(PresentModel present);

    CreatePresentCommand toCreateCommand(UUID id, DAddPresent present);

    CreatePresentCommand.PresentItem toCreateCommandItems(DPresentItem items);
}
