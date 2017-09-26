package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.model.PresentItem;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.query.candy.CandyMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static ru.home.db.Tables.*;
import static ru.home.shop.utils.UuidUtils.newUUID;

@Repository
public class JOOQPresentRepository implements PresentRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQPresentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional
    public void add(Present present) {
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

    @Override
    public int remove(UUID id) {
        return dsl.deleteFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .execute();
    }

    @Override
    public Collection<Present> listView() {
        return dsl.selectFrom(PRESENT)
                .fetch(new PresentMapper());
    }

    @Override
    @Transactional
    public Present findById(UUID id) {
        Present present = dsl.selectFrom(PRESENT)
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
