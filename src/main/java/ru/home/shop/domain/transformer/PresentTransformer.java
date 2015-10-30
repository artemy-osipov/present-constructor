package ru.home.shop.domain.transformer;

import ru.home.shop.db.tables.records.PresentRecord;
import ru.home.shop.domain.bean.PresentBean;

public class PresentTransformer {

    public static PresentBean transform(PresentRecord record) {
        if (record == null) {
            return null;
        }

        PresentBean bean = new PresentBean();
        bean.setId(record.getId());
        bean.setName(record.getName());
        bean.setPrice(record.getPrice());

        return bean;
    }
}
