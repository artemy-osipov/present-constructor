package ru.home.shop.domain.repo.jooq;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.domain.model.Candy;

public class CandyMapper implements RecordMapper<CandyRecord, Candy>{

    @Override
    public Candy map(CandyRecord record) {
        if (record == null) {
            return null;
        }

        Candy bean = new Candy();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setFirm(record.getFirm());
        bean.setPrice(record.getPrice());
        bean.setOrder(record.getOrder());

        return bean;
    }
}
