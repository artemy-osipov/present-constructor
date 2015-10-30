package ru.home.shop.domain.transformer;

import org.junit.Test;
import ru.home.shop.db.tables.records.PresentRecord;
import ru.home.shop.domain.bean.PresentBean;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PresentTransformerTest {

    private static final int ID = 1;
    private static final String NAME = "name";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);

    private PresentRecord getRecord() {
        PresentRecord record = new PresentRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setPrice(PRICE);

        return record;
    }

    @Test
    public void transform_valid_shouldTransformEntry() {
        PresentBean present = PresentTransformer.transform(getRecord());

        assertEquals(ID, present.getId());
        assertEquals(NAME, present.getName());
        assertNotNull(present.getPrice());
        assertEquals(PRICE, present.getPrice());
    }

    @Test
    public void transform_nullEntry_shouldReturnNull() {
        assertNull(PresentTransformer.transform(null));
    }
}