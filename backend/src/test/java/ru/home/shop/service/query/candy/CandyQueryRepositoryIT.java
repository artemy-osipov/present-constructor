package ru.home.shop.service.query.candy;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.shop.service.DBRiderIT;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyQueryRepositoryIT extends DBRiderIT {

    private static final UUID CANDY_ID = UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c");
    private static final String CANDY_NAME = "name";
    private static final String CANDY_FIRM = "firm";
    private static final BigDecimal CANDY_PRICE = new BigDecimal("2.60");
    private static final Double CANDY_ORDER = 1.1;

    @Autowired
    private CandyQueryRepository repository;

    public CandyQueryRepositoryIT() {
        cleanDataAfterClass(CANDY);
    }

    private CandyQuery candyQuery() {
        CandyQuery candy = new CandyQuery();
        candy.setId(CANDY_ID);
        candy.setName(CANDY_NAME);
        candy.setFirm(CANDY_FIRM);
        candy.setPrice(CANDY_PRICE);
        candy.setOrder(CANDY_ORDER);

        return candy;
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    public void listShouldReturnEmptyCollectionIfNoRecords() {
        assertThat(repository.list()).isEmpty();
    }

    @Test
    @DataSet("candy/candy_list.yml")
    public void listShouldReturnActiveCandies() {
        Collection<CandyQuery> candies = repository.list();
        assertThat(candies).hasSize(1);
        assertCandy(candies.iterator().next());
    }

    @Test
    @DataSet("candy/candy.yml")
    public void findByExistentIdShouldReturnEntry() {
        CandyQuery candy = repository.findById(CANDY_ID);
        assertCandy(candy);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        CandyQuery candy = repository.findById(newUUID());
        assertThat(candy).isNull();
    }

    private void assertCandy(CandyQuery candy) {
        assertThat(candy).isEqualToComparingFieldByField(candyQuery());
    }
}