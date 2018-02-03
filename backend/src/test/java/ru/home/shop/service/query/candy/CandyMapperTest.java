package ru.home.shop.service.query.candy;

import org.junit.jupiter.api.Test;
import ru.home.db.tables.records.CandyRecord;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

class CandyMapperTest {

    private static final UUID ID = newUUID();
    private static final String NAME = "name";
    private static final String FIRM = "firm";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);
    private static final double ORDER = 1.1;

    private final CandyMapper mapper = new CandyMapper();

    private CandyRecord record() {
        CandyRecord record = new CandyRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setFirm(FIRM);
        record.setPrice(PRICE);
        record.setOrder(ORDER);

        return record;
    }

    private CandyQuery candyQuery() {
        CandyQuery candy = new CandyQuery();
        candy.setId(ID);
        candy.setName(NAME);
        candy.setFirm(FIRM);
        candy.setPrice(PRICE);
        candy.setOrder(ORDER);

        return candy;
    }

    @Test
    void nullShouldMapToNull() {
        CandyQuery mapped = mapper.map(null);
        assertThat(mapped).isNull();
    }

    @Test
    void testFullInfoMap() {
        CandyQuery candy = mapper.map(record());
        assertThat(candy).isEqualToComparingFieldByField(candyQuery());
    }
}