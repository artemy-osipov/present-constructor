package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.bean.PresentBean;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.domain.transformer.PresentTransformer;

import java.util.Collection;

import static ru.home.shop.db.tables.Candy.CANDY;
import static ru.home.shop.db.tables.CandyPresent.CANDY_PRESENT;
import static ru.home.shop.db.tables.Present.PRESENT;

@Repository
public class JOOQPresentRepository implements PresentRepository {

    private DSLContext dsl;

    @Autowired
    public JOOQPresentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional
    public int addFull(PresentBean present) {
        int id = dsl.insertInto(PRESENT)
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .returning(PRESENT.ID)
                .fetchOne().getId();

        addCandiesToPresent(id, present.getCandies());

        return id;
    }

    private void addCandiesToPresent(int present, Collection<CandyBean> candies) {
        candies.forEach(
                candy -> dsl.insertInto(CANDY_PRESENT)
                        .set(CANDY_PRESENT.PRESENT, present)
                        .set(CANDY_PRESENT.CANDY, candy.getId())
                        .set(CANDY_PRESENT.COUNT, candy.getCount())
                        .execute()
        );
    }

    @Override
    public int remove(int id) {
        return dsl.deleteFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .execute();
    }

    @Override
    @Transactional
    public int editFull(PresentBean present) {
        int updated = dsl.update(PRESENT)
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .where(PRESENT.ID.eq(present.getId()))
                .execute();

        dsl.deleteFrom(CANDY_PRESENT)
                .where(CANDY_PRESENT.PRESENT.eq(present.getId()));

        addCandiesToPresent(present.getId(), present.getCandies());

        return updated;
    }

    @Override
    public Collection<PresentBean> findAll() {
        return dsl.selectFrom(PRESENT)
                .fetch().map(PresentTransformer::transform);
    }

    @Override
    @Transactional
    public PresentBean findFull(int id) {
        PresentBean present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOne(PresentTransformer::transform);

        if (present != null) {
            present.setCandies(listCandiesByPresent(id));
        }

        return present;
    }

    private Collection<CandyBean> listCandiesByPresent(int present) {
        return dsl.select()
                .from(CANDY_PRESENT)
                .leftJoin(CANDY).on(CANDY_PRESENT.CANDY.eq(CANDY.ID))
                .where(CANDY_PRESENT.PRESENT.eq(present))
                .orderBy(CANDY.CANDY_ORDER)
                .fetch()
                .map(r -> {
                    CandyBean candy = new CandyBean();
                    candy.setId(r.getValue(CANDY.ID));
                    candy.setName(r.getValue(CANDY.NAME));
                    candy.setFirm(r.getValue(CANDY.FIRM));
                    candy.setPrice(r.getValue(CANDY.PRICE));
                    candy.setOrder(r.getValue(CANDY.CANDY_ORDER).doubleValue());
                    candy.setCount(r.getValue(CANDY_PRESENT.COUNT));

                    return candy;
                });
    }
}
