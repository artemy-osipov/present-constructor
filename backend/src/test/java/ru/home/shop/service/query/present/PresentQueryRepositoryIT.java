package ru.home.shop.service.query.present;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.home.shop.service.query.candy.CandyMapperImpl;
import ru.home.shop.utils.db.DBTest;
import ru.home.shop.utils.db.DatabaseConfig;
import ru.home.shop.service.query.candy.CandyQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.home.shop.utils.UuidUtils.newUUID;

@ExtendWith(SpringExtension.class)
@DBTest
@ContextHierarchy({
        @ContextConfiguration(classes = DatabaseConfig.class),
        @ContextConfiguration(classes = {PresentQueryRepository.class, CandyMapperImpl.class, PresentMapperImpl.class})
})
class PresentQueryRepositoryIT {

    private static final UUID PRESENT_ID = UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985");

    @Autowired
    private PresentQueryRepository repository;

    private PresentQuery presentQuery() {
        PresentQuery present = new PresentQuery();
        present.setId(PRESENT_ID);
        present.setName("name");
        present.setPrice(BigDecimal.valueOf(12.35));
        present.setDate(LocalDateTime.of(2017, 1, 1, 12, 0));

        List<PresentItemQuery> items = new ArrayList<>();
        PresentItemQuery item1 = new PresentItemQuery();
        item1.setCandy(new CandyQuery());
        item1.getCandy().setId(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
        item1.setCount(6);
        items.add(item1);

        PresentItemQuery item2 = new PresentItemQuery();
        item2.setCandy(new CandyQuery());
        item2.getCandy().setId(UUID.fromString("a764c765-483c-492b-ac63-4f2c4f6d2ff4"));
        item2.setCount(2);
        items.add(item2);

        present.setItems(items);

        return present;
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    void listShouldReturnCorrectRecords() {
        Collection<PresentQuery> presents = repository.list();
        assertThat(presents).hasSize(1);
        assertPresent(presents.iterator().next(), false);
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    void findByExistentIdShouldReturnValidEntry() {
        PresentQuery present = repository.findById(PRESENT_ID).get();
        assertPresent(present, true);
    }

    @Test
    void findByNonexistentIdShouldReturnNull() {
        assertThat(repository.findById(newUUID()))
                .isEmpty();
    }

    private void assertPresent(PresentQuery actual, boolean assertItems) {
        PresentQuery expected = presentQuery();

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "items");

        if (assertItems) {
            assertThat(actual.getItems())
                    .hasSize(2)
                    .usingElementComparatorOnFields("candy.id", "count").containsExactlyElementsOf(expected.getItems());
        }
    }
}