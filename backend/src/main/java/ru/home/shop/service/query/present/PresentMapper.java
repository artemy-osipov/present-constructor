package ru.home.shop.service.query.present;

import org.jooq.RecordMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.home.db.tables.records.PresentRecord;
import ru.home.shop.config.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface PresentMapper extends RecordMapper<PresentRecord, PresentQuery> {

    @Override
    @Mapping(target = "items", ignore = true)
    PresentQuery map(PresentRecord record);
}
