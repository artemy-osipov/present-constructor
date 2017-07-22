package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.model.Present;
import ru.home.shop.domain.repo.PresentRepository;
import ru.home.shop.domain.repo.mapper.PresentMapper;

import java.util.Collection;

import static ru.home.db.Tables.CANDY_HISTORY;
import static ru.home.db.tables.Candy.CANDY;
import static ru.home.db.tables.CandyPresent.CANDY_PRESENT;
import static ru.home.db.tables.Present.PRESENT;

@Repository
public class JOOQPresentRepository implements PresentRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQPresentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Transactional
    public int add(Present present) {
        int id = dsl.insertInto(PRESENT)
                .set(PRESENT.NAME, present.getName())
                .set(PRESENT.PRICE, present.getPrice())
                .returning(PRESENT.ID)
                .fetchOne().getId();

        addCandiesToPresent(id, present.getCandies());

        return id;
    }

    private void addCandiesToPresent(int present, Collection<Candy> candies) {
        candies.forEach(
                candy -> dsl.insertInto(CANDY_PRESENT)
                        .set(CANDY_PRESENT.PRESENT, present)
                        .set(CANDY_PRESENT.CANDY_HISTORY, candy.getVid())
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
    public int edit(Present present) {
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
    public Collection<Present> findAll() {
        return dsl.selectFrom(PRESENT)
                .fetch(new PresentMapper());
    }

    @Override
    @Transactional
    public Present findFull(int id) {
        Present present = dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOne(new PresentMapper());

        if (present != null) {
            present.setCandies(listCandiesByPresent(id));
        }

        return present;
    }

    private Collection<Candy> listCandiesByPresent(int present) {
        return dsl.select()
                .from(CANDY_PRESENT)
                .leftJoin(CANDY_HISTORY).on(CANDY_PRESENT.CANDY_HISTORY.eq(CANDY_HISTORY.ID))
                .leftJoin(CANDY).on(CANDY_HISTORY.CANDY.eq(CANDY.ID))
                .where(CANDY_PRESENT.PRESENT.eq(present))
                .orderBy(CANDY.ORDER)
                .fetch()
                .map(r -> {
                    Candy candy = new Candy();
                    candy.setId(r.getValue(CANDY_HISTORY.CANDY));
                    candy.setVid(r.getValue(CANDY_HISTORY.ID));
                    candy.setName(r.getValue(CANDY_HISTORY.NAME));
                    candy.setFirm(r.getValue(CANDY_HISTORY.FIRM));
                    candy.setPrice(r.getValue(CANDY_HISTORY.PRICE));
                    candy.setOrder(r.getValue(CANDY.ORDER));
                    candy.setCount(r.getValue(CANDY_PRESENT.COUNT));

                    return candy;
                });
    }
}
