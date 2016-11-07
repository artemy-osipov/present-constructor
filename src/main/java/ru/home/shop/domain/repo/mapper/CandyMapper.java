package ru.home.shop.domain.repo.mapper;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.home.shop.domain.bean.CandyBean;

import static ru.home.db.Tables.CANDY;
import static ru.home.db.Tables.CANDY_HISTORY;

public class CandyMapper implements RecordMapper<Record, CandyBean>{

    @Override
    public CandyBean map(Record record) {
        if (record == null) {
            return null;
        }

        CandyBean bean = new CandyBean();
        bean.setId(record.getValue(CANDY.ID));
        bean.setVid(record.getValue(CANDY_HISTORY.ID));
        bean.setName(record.getValue(CANDY.NAME));
        bean.setFirm(record.getValue(CANDY.FIRM));
        bean.setPrice(record.getValue(CANDY.PRICE));
        bean.setOrder(record.getValue(CANDY.ORDER));

        return bean;
    }
}
