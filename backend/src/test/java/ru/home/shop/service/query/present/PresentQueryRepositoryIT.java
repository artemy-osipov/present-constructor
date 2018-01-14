package ru.home.shop.service.query.present;

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
import static ru.home.db.Tables.PRESENT;
import static ru.home.shop.utils.UuidUtils.newUUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PresentQueryRepositoryIT extends DBRiderIT {

    @Autowired
    private PresentQueryRepository repository;

    public PresentQueryRepositoryIT() {
        cleanDataAfterClass(PRESENT, CANDY);
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    public void listShouldReturnCorrectRecords() {
        Collection<PresentQuery> presents = repository.list();
        assertThat(presents).hasSize(1);
        assertPresent(presents.iterator().next(), false);
    }

    @Test
    @DataSet({"candy/candy_list.yml", "present/present.yml"})
    public void findByExistentIdShouldReturnValidEntry() {
        PresentQuery present = repository.findById(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985"));
        assertPresent(present, true);
    }

    @Test
    public void findByNonexistentIdShouldReturnNull() {
        PresentQuery present = repository.findById(newUUID());
        assertThat(present).isNull();
    }

    private void assertPresent(PresentQuery present, boolean assertItems) {
        assertThat(present.getId()).isEqualTo(UUID.fromString("9744b2ea-2328-447c-b437-a4f8b57c9985"));
        assertThat(present.getName()).isEqualTo("name");
        assertThat(present.getPrice()).isEqualTo(new BigDecimal("12.35"));

        if (assertItems) {
            assertThat(present.getItems()).hasSize(2);
            assertThat(present.getItems().get(0).getCandy().getId()).isEqualTo(UUID.fromString("7a8d3659-81e8-49aa-80fb-3121fee7c29c"));
            assertThat(present.getItems().get(0).getCount()).isEqualTo(6);
            assertThat(present.getItems().get(1).getCandy().getId()).isEqualTo(UUID.fromString("a764c765-483c-492b-ac63-4f2c4f6d2ff4"));
            assertThat(present.getItems().get(1).getCount()).isEqualTo(2);
        }
    }
}