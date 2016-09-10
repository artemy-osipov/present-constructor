package ru.home.shop.domain.repo.mapper;

import org.junit.Test;
import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.domain.bean.CandyBean;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CandyMapperTest {

    private static final Integer ID = 1;
    private static final String NAME = "name";
    private static final String FIRM = "firm";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);
    private static final double ORDER = 1.1;

    private CandyMapper mapper = new CandyMapper();

    private CandyRecord getRecord() {
        CandyRecord record = new CandyRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setFirm(FIRM);
        record.setPrice(PRICE);
        record.setCandyOrder(BigDecimal.valueOf(ORDER));

        return record;
    }

    @Test
    public void nullShouldMapToNull() {
        assertNull(mapper.map(null));
    }

    @Test
    public void testFullInfoMap() {
        CandyBean candy = mapper.map(getRecord());

        assertEquals(ID, candy.getId());
        assertEquals(NAME, candy.getName());
        assertEquals(FIRM, candy.getFirm());
        assertNotNull(candy.getPrice());
        assertEquals(PRICE, candy.getPrice());
        assertEquals(ORDER, candy.getOrder(), 0.001);
    }

    @Test
    public void nullOrderShouldMapToZero() {
        CandyRecord record = getRecord();
        record.setCandyOrder(null);

        assertEquals(0, mapper.map(record).getOrder(), 0.001);
    }
}