package ru.home.shop.query.present;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.PresentRecord;

class PresentMapper implements RecordMapper<PresentRecord, PresentQuery> {

    @Override
    public PresentQuery map(PresentRecord record) {
        if (record == null) {
            return null;
        }

        PresentQuery bean = new PresentQuery();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setPrice(record.getPrice());
        bean.setDate(record.getDate());

        return bean;
    }
}
