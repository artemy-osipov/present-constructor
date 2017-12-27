package ru.home.shop.service.query.candy;

import org.junit.Test;
import ru.home.db.tables.records.CandyRecord;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class CandyMapperTest {

    private static final UUID ID = newUUID();
    private static final String NAME = "name";
    private static final String FIRM = "firm";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);
    private static final double ORDER = 1.1;

    private final CandyMapper mapper = new CandyMapper();

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
        CandyQuery mapped = mapper.map(null);
        assertThat(mapped, nullValue());
    }

    @Test
    public void testFullInfoMap() {
        CandyQuery candy = mapper.map(getRecord());

        assertThat(candy.getId(), equalTo(ID));
        assertThat(candy.getName(), equalTo(NAME));
        assertThat(candy.getFirm(), equalTo(FIRM));
        assertThat(candy.getPrice(), equalTo(PRICE));
        assertThat(candy.getOrder(), equalTo(ORDER));
    }
}