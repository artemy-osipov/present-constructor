package ru.home.shop.query.present;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.query.candy.CandyMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static ru.home.db.Tables.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@Repository
public class PresentEntryRepository {

    private final DSLContext dsl;

    @Autowired
    public PresentEntryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void add(PresentEntry present) {
        dsl.insertInto(PRESENT)
                .set(PRESENT.ID, present.getId())
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .set(PRESENT.DATE, present.getDate())
                .execute();

        addPresentItem(present.getId(), present.getItems());
    }

    private void addPresentItem(UUID presentId, Collection<PresentItem> items) {
        items.forEach(
                item -> dsl.insertInto(PRESENT_ITEM)
                        .set(PRESENT_ITEM.ID, newUUID())
                        .set(PRESENT_ITEM.PRESENT, presentId)
                        .set(PRESENT_ITEM.CANDY, item.getCandy().getId())
                        .set(PRESENT_ITEM.COUNT, item.getCount())
                        .execute()
        );
    }

    public int remove(UUID id) {
        return dsl.deleteFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .execute();
    }

    public Collection<PresentEntry> list() {
        return dsl.selectFrom(PRESENT)
                .fetch(new PresentMapper());
    }

    public PresentEntry findById(UUID id) {
        PresentEntry present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOne(new PresentMapper());

        if (present != null) {
            present.setItems(listPresentItem(id));
        }

        return present;
    }

    private List<PresentItem> listPresentItem(UUID present) {
        return dsl.select()
                .from(PRESENT_ITEM)
                .leftJoin(CANDY).on(PRESENT_ITEM.CANDY.eq(CANDY.ID))
                .where(PRESENT_ITEM.PRESENT.eq(present))
                .orderBy(CANDY.ORDER)
                .fetch()
                .map(r -> {
                    PresentItem item = new PresentItem();
                    item.setCandy(new CandyMapper().map(r.into(CANDY)));
                    item.setCount(r.getValue(PRESENT_ITEM.COUNT));

                    return item;
                });
    }
}
