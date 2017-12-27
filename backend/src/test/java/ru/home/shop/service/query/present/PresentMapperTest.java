package ru.home.shop.service.query.present;

import org.junit.Test;
import ru.home.db.tables.records.PresentRecord;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

public class PresentMapperTest {

    private static final UUID ID = newUUID();
    private static final String NAME = "name";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);

    private final PresentMapper mapper = new PresentMapper();

    private PresentRecord getRecord() {
        PresentRecord record = new PresentRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setPrice(PRICE);

        return record;
    }

    @Test
    public void nullShouldMapToNull() {
        PresentQuery mapped = mapper.map(null);
        assertThat(mapped, nullValue());
    }

    @Test
    public void testFullInfoMap() {
        PresentQuery present = mapper.map(getRecord());

        assertThat(present.getId(), equalTo(ID));
        assertThat(present.getName(), equalTo(NAME));
        assertThat(present.getPrice(), equalTo(PRICE));
    }
}