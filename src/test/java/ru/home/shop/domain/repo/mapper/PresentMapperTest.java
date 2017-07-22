package ru.home.shop.domain.repo.mapper;

import org.junit.Test;
import ru.home.db.tables.records.PresentRecord;
import ru.home.shop.domain.model.Present;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PresentMapperTest {

    private static final Integer ID = 1;
    private static final String NAME = "name";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);

    private PresentMapper mapper = new PresentMapper();

    private PresentRecord getRecord() {
        PresentRecord record = new PresentRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setPrice(PRICE);

        return record;
    }

    @Test
    public void nullShouldMapToNull() {
        assertNull(mapper.map(null));
    }

    @Test
    public void testFullInfoMap() {
        Present present = mapper.map(getRecord());

        assertEquals(ID, present.getId());
        assertEquals(NAME, present.getName());
        assertEquals(PRICE, present.getPrice());
    }
}