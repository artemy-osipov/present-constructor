package ru.home.shop.query.present;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.PresentRecord;
import ru.home.shop.domain.Present;

public class PresentMapper implements RecordMapper<PresentRecord, PresentEntry> {

    @Override
    public PresentEntry map(PresentRecord record) {
        if (record == null) {
            return null;
        }

        PresentEntry bean = new PresentEntry();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setPrice(record.getPrice());
        bean.setDate(record.getDate());

        return bean;
    }
}
