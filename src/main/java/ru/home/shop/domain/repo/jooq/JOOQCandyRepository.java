package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.repo.mapper.CandyMapper;

import java.util.Collection;

import static ru.home.db.tables.Candy.CANDY;

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

    @Override
    public int remove(int id) {
        return dsl.update(CANDY)
                .set(CANDY.ACTIVE, false)
                .where(CANDY.ID.eq(id))
                .execute();
    }

    @Override
    public int edit(Candy candy) {
        return dsl.update(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .where(CANDY.ID.eq(candy.getId()))
                .execute();
    }

    @Override
    public Collection<Candy> findAll() {
        return dsl.select()
                .from(CANDY)
                .where(CANDY.ACTIVE.eq(true))
                .orderBy(CANDY.ORDER)
                .fetch(new CandyMapper());
    }

    @Override
    public Candy find(int id) {
        return dsl.select()
                .from(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOne(new CandyMapper());
    }
}
