package ru.home.shop.domain.repo.jooq;

import com.fasterxml.uuid.Generators;
import org.junit.Test;
import ru.home.db.tables.records.CandyRecord;
import ru.home.shop.domain.model.Candy;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CandyMapperTest {

    private static final UUID ID = Generators.timeBasedGenerator().generate();
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
        record.setOrder(ORDER);

        return record;
    }

    @Test
    public void nullShouldMapToNull() {
        assertNull(mapper.map(null));
    }

    @Test
    public void testFullInfoMap() {
        Candy candy = mapper.map(getRecord());

        assertEquals(ID, candy.getId());
        assertEquals(NAME, candy.getName());
        assertEquals(FIRM, candy.getFirm());
        assertEquals(PRICE, candy.getPrice());
        assertEquals(ORDER, candy.getOrder(), 0.001);
    }
}