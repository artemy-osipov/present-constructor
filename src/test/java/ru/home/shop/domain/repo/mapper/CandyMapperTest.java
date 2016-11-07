package ru.home.shop.domain.repo.mapper;

import org.jooq.Record;
import org.junit.Test;
import ru.home.shop.domain.bean.CandyBean;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.home.db.Tables.CANDY;
import static ru.home.db.Tables.CANDY_HISTORY;

public class CandyMapperTest {

    private static final Integer ID = 1;
    private static final Integer VID = 10;
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
        when(record.getValue(CANDY_HISTORY.ID)).thenReturn(VID);

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
        assertEquals(VID, candy.getVid());
        assertEquals(NAME, candy.getName());
        assertEquals(FIRM, candy.getFirm());
        assertEquals(PRICE, candy.getPrice());
        assertEquals(ORDER, candy.getOrder(), 0.001);
    }
}