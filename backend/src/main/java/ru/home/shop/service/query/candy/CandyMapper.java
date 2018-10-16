package ru.home.shop.service.query.candy;

import org.jooq.RecordMapper;
import org.mapstruct.Mapper;
import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.config.MapStructConfig;

@Mapper(config = MapStructConfig.class)
public interface CandyMapper extends RecordMapper<CandyRecord, CandyQuery> {

    @Override
    CandyQuery map(CandyRecord record);
}
