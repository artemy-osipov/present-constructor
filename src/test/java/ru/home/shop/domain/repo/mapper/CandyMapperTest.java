package ru.home.shop.domain.repo.mapper;

import com.fasterxml.uuid.Generators;
import org.jooq.Record;
import org.junit.Test;
import ru.home.shop.domain.model.Candy;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.home.db.Tables.CANDY;

public class CandyMapperTest {

    private static final UUID ID = Generators.timeBasedGenerator().generate();
    private static final String NAME = "name";
    private static final String FIRM = "firm";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);
    private static final double ORDER = 1.1;

    private CandyMapper mapper = new CandyMapper();

    private Record getRecord() {
        Record record = mock(Record.class);

        when(record.getValue(CANDY.ID)).thenReturn(ID);
        when(record.getValue(CANDY.NAME)).thenReturn(NAME);
        when(record.getValue(CANDY.FIRM)).thenReturn(FIRM);
        when(record.getValue(CANDY.PRICE)).thenReturn(PRICE);
        when(record.getValue(CANDY.ORDER)).thenReturn(ORDER);

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