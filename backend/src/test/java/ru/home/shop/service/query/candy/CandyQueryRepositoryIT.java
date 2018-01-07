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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static ru.home.db.Tables.CANDY;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandyQueryRepositoryIT extends DBRiderIT {

    @Autowired
    private CandyQueryRepository repository;

    public CandyQueryRepositoryIT() {
        cleanDataAfterClass(CANDY);
    }

    @Test
    @DataSet("candy/candy_empty.yml")
    public void listShouldReturnEmptyCollectionIfNoRecords() {
        assertThat(repository.list(), empty());
    }

    @Test
    @DataSet("candy/candy_list.yml")
    public void listShouldReturnActiveCandies() {
        Collection<CandyQuery> candies = repository.list();
        assertThat(candies, hasSize(1));
        assertCandy(candies.iterator().next());
    }

    @Test
    @DataSet("candy/candy.yml")
    public void findByExistentIdShouldReturnValidEntry() {
        CandyQuery candy = repository.findById(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        assertCandy(candy);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        CandyQuery candy = repository.findById(newUUID());
        assertThat(candy, nullValue());
    }

    private void assertCandy(CandyQuery candy) {
        assertThat(candy.getId(), equalTo(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c")));
        assertThat(candy.getName(), equalTo("name"));
        assertThat(candy.getFirm(), equalTo("firm"));
        assertThat(candy.getPrice(), equalTo(new BigDecimal("2.60")));
        assertThat(candy.getOrder(), equalTo(1.1));
    }
}