package ru.home.shop.service.query.present;

import org.junit.jupiter.api.Test;
import ru.home.db.tables.records.PresentRecord;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

class PresentMapperTest {

    private static final UUID ID = newUUID();
    private static final String NAME = "name";
    private static final BigDecimal PRICE = BigDecimal.valueOf(2.3);

    private final PresentMapper mapper = new PresentMapper();

    private PresentRecord record() {
        PresentRecord record = new PresentRecord();
        record.setId(ID);
        record.setName(NAME);
        record.setPrice(PRICE);

        return record;
    }

    private PresentQuery presentQuery() {
        PresentQuery present = new PresentQuery();
        present.setId(ID);
        present.setName(NAME);
        present.setPrice(PRICE);

        return present;
    }

    @Test
    void nullShouldMapToNull() {
        PresentQuery mapped = mapper.map(null);
        assertThat(mapped).isNull();
    }

    @Test
    void testFullInfoMap() {
        PresentQuery present = mapper.map(record());
        assertThat(present).isEqualToComparingFieldByField(presentQuery());
    }
}