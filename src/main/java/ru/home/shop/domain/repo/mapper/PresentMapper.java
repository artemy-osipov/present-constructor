package ru.home.shop.domain.repo.mapper;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.PresentRecord;
import ru.home.shop.domain.model.Present;

public class PresentMapper implements RecordMapper<PresentRecord, Present>{

    @Override
    public Present map(PresentRecord record) {
        if (record == null) {
            return null;
        }

        Present bean = new Present();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setPrice(record.getPrice());

        return bean;
    }
}
