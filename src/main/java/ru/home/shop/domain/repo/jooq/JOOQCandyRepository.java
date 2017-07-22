package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.repo.mapper.CandyMapper;

import java.util.Collection;

import static ru.home.db.tables.Candy.CANDY;
import static ru.home.db.tables.CandyHistory.CANDY_HISTORY;

@Repository
public class JOOQCandyRepository implements CandyRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQCandyRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int add(Candy candy) {
        int candyId = addCandy(candy);
        candy.setId(candyId);

        int versionId = addVersion(candy);
        candy.setVid(versionId);

        return candyId;
    }

    private int addCandy(Candy candy) {
        return dsl.insertInto(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .returning(CANDY.ID)
                .fetchOne()
                .getId();
    }

    private int addVersion(Candy candy) {
        dsl.update(CANDY_HISTORY)
                .set(CANDY_HISTORY.LAST, false)
                .where(CANDY_HISTORY.CANDY.eq(candy.getId()));

        return dsl.insertInto(CANDY_HISTORY)
                .set(CANDY_HISTORY.CANDY, candy.getId())
                .set(CANDY_HISTORY.LAST, true)
                .set(CANDY_HISTORY.NAME, candy.getName())
                .set(CANDY_HISTORY.FIRM, candy.getFirm())
                .set(CANDY_HISTORY.PRICE, candy.getPrice())
                .returning(CANDY_HISTORY.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public int remove(int id) {
        return dsl.update(CANDY)
                .set(CANDY.ACTIVE, false)
                .where(CANDY.ID.eq(id))
                .execute();
    }

    @Override
    public int edit(Candy candy) {
        int updated = dsl.update(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .where(CANDY.ID.eq(candy.getId()))
                .execute();

        if (updated == 1) {
            dsl.update(CANDY_HISTORY)
                    .set(CANDY_HISTORY.LAST, false)
                    .where(CANDY_HISTORY.CANDY.eq(candy.getId()))
                    .execute();

            int versionId = addVersion(candy);
            candy.setVid(versionId);
        }

        return updated;
    }

    @Override
    public Collection<Candy> findAll() {
        return dsl.select()
                .from(CANDY)
                .leftJoin(CANDY_HISTORY).on(CANDY_HISTORY.CANDY.eq(CANDY.ID).and(CANDY_HISTORY.LAST.eq(true)))
                .where(CANDY.ACTIVE.eq(true))
                .orderBy(CANDY.ORDER)
                .fetch(new CandyMapper());
    }

    @Override
    public Candy find(int id) {
        return dsl.select()
                .from(CANDY)
                .leftJoin(CANDY_HISTORY).on(CANDY_HISTORY.CANDY.eq(CANDY.ID).and(CANDY_HISTORY.LAST.eq(true)))
                .where(CANDY.ID.eq(id))
                .fetchOne(new CandyMapper());
    }
}
