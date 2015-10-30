package ru.home.shop.domain.transformer;

import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.domain.bean.CandyBean;

public class CandyTransformer {

    public static CandyBean transform(CandyRecord record) {
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
