package ru.home.shop.query.present;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.query.candy.CandyMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static ru.home.db.Tables.*;

@Repository
public class PresentQueryRepository {

    private final DSLContext dsl;

    @Autowired
    public PresentQueryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Collection<PresentQuery> list() {
        return dsl.selectFrom(PRESENT)
                .fetch(new PresentMapper());
    }

    public PresentQuery findById(UUID id) {
        PresentQuery present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOne(new PresentMapper());

        if (present != null) {
            present.setItems(listPresentItem(id));
        }

        return present;
    }

    private List<PresentItemQuery> listPresentItem(UUID present) {
        return dsl.select()
                .from(PRESENT_ITEM)
                .leftJoin(CANDY).on(PRESENT_ITEM.CANDY_ID.eq(CANDY.ID))
                .where(PRESENT_ITEM.PRESENT_ID.eq(present))
                .orderBy(CANDY.ORDER)
                .fetch()
                .map(r -> {
                    PresentItemQuery item = new PresentItemQuery();
                    item.setCandy(new CandyMapper().map(r.into(CANDY)));
                    item.setCount(r.getValue(PRESENT_ITEM.COUNT));

                    return item;
                });
    }
}
