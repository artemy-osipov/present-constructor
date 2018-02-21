package ru.home.shop.service.query.candy;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.home.shop.utils.db.DBTest;
import ru.home.shop.utils.db.DatabaseConfig;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@DBTest
@ContextHierarchy({
        @ContextConfiguration(classes = DatabaseConfig.class),
        @ContextConfiguration(classes = CandyQueryRepository.class)
})
class CandyQueryRepositoryIT {

    private static final UUID CANDY_ID = UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c");
    private static final String CANDY_NAME = "name";
    private static final String CANDY_FIRM = "firm";
    private static final BigDecimal CANDY_PRICE = new BigDecimal("2.60");
    private static final Double CANDY_ORDER = 1.1;

    @Autowired
    private CandyQueryRepository repository;

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
    void listShouldReturnEmptyCollectionIfNoRecords() {
        assertThat(repository.list()).isEmpty();
    }

    @Test
    @DataSet("candy/candy_list.yml")
    void listShouldReturnActiveCandies() {
        Collection<CandyQuery> candies = repository.list();
        assertThat(candies).hasSize(1);
        assertCandy(candies.iterator().next());
    }

    @Test
    @DataSet("candy/candy.yml")
    void findByExistentIdShouldReturnEntry() {
        CandyQuery candy = repository.findById(CANDY_ID);
        assertCandy(candy);
    }

    @Test
    void findByNonexistentIdShouldReturnNull() {
        CandyQuery candy = repository.findById(newUUID());
        assertThat(candy).isNull();
    }

    private void assertCandy(CandyQuery candy) {
        assertThat(candy).isEqualToComparingFieldByField(candyQuery());
    }
}