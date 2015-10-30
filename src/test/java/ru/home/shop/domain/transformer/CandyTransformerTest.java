package ru.home.shop.domain.transformer;

import org.junit.Test;
import ru.home.shop.db.tables.records.CandyRecord;
import ru.home.shop.domain.bean.CandyBean;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CandyTransformerTest {

    private static final int ID = 1;
    private static final String NAME = "name";
    private static final String FIRM = "firm";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);
    private static final double ORDER = 1.1;

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
    public void transform_valid_shouldTransformEntry() {
        CandyBean candy = CandyTransformer.transform(getRecord());

        assertEquals(ID, candy.getId());
        assertEquals(NAME, candy.getName());
        assertEquals(FIRM, candy.getFirm());
        assertNotNull(candy.getPrice());
        assertEquals(PRICE, candy.getPrice());
        assertEquals(ORDER, candy.getOrder(), 0.001);
    }

    @Test
    public void transform_nullEntry_shouldReturnNull() {
        assertNull(CandyTransformer.transform(null));
    }

    @Test
    public void transform_nullOrder_shouldNotTransformOrder() {
        CandyRecord record = getRecord();
        record.setCandyOrder(null);

        assertEquals(0, CandyTransformer.transform(record).getOrder(), 0.001);
    }
}