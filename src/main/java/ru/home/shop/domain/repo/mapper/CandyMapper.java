package ru.home.shop.domain.repo.mapper;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.home.shop.domain.model.Candy;

import static ru.home.db.Tables.CANDY;
import static ru.home.db.Tables.CANDY_HISTORY;

public class CandyMapper implements RecordMapper<Record, Candy>{

    @Override
    public Candy map(Record record) {
        if (record == null) {
            return null;
        }

        Candy bean = new Candy();
        bean.setId(record.getValue(CANDY.ID));
        bean.setVid(record.getValue(CANDY_HISTORY.ID));
        bean.setName(record.getValue(CANDY.NAME));
        bean.setFirm(record.getValue(CANDY.FIRM));
        bean.setPrice(record.getValue(CANDY.PRICE));
        bean.setOrder(record.getValue(CANDY.ORDER));

        return bean;
    }
}
