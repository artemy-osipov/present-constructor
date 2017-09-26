package ru.home.shop.query.candy;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.CandyRecord;

public class CandyMapper implements RecordMapper<CandyRecord, CandyEntry> {

    @Override
    public CandyEntry map(CandyRecord record) {
        if (record == null) {
            return null;
        }

        CandyEntry bean = new CandyEntry();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setFirm(record.getFirm());
        bean.setPrice(record.getPrice());
        bean.setOrder(record.getOrder());

        return bean;
    }
}
