package ru.home.shop.service.query.candy;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.CandyRecord;

public class CandyMapper implements RecordMapper<CandyRecord, CandyQuery> {

    @Override
    public CandyQuery map(CandyRecord record) {
        if (record == null) {
            return null;
        }

        CandyQuery bean = new CandyQuery();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setFirm(record.getFirm());
        bean.setPrice(record.getPrice());
        bean.setOrder(record.getOrder());

        return bean;
    }
}
