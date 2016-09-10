package ru.home.shop.domain.repo.mapper;

import org.jooq.RecordMapper;
import ru.home.db.tables.records.PresentRecord;
import ru.home.shop.domain.bean.PresentBean;

public class PresentMapper implements RecordMapper<PresentRecord, PresentBean>{

    @Override
    public PresentBean map(PresentRecord record) {
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
