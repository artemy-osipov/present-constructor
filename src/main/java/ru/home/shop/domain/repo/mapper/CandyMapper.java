package ru.home.shop.domain.repo.mapper;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.domain.bean.CandyBean;

public class CandyMapper implements RecordMapper<CandyRecord, CandyBean>{

    @Override
    public CandyBean map(CandyRecord record) {
        if (record == null) {
            return null;
        }

        CandyBean bean = new CandyBean();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setFirm(record.getFirm());
        bean.setPrice(record.getPrice());

        if (record.getCandyOrder() != null) {
            bean.setOrder(record.getCandyOrder().doubleValue());
        }

        return bean;
    }
}
