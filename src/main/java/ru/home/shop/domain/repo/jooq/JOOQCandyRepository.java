package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.domain.model.Candy;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.repo.mapper.CandyMapper;

import java.util.Collection;
import java.util.UUID;

import static ru.home.db.tables.Candy.CANDY;

@Repository
public class JOOQCandyRepository implements CandyRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQCandyRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void add(Candy candy) {
        dsl.insertInto(CANDY)
                .set(CANDY.ID, candy.getId())
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .execute();
    }

    @Override
    public int remove(UUID id) {
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
    public Candy find(UUID id) {
        return dsl.select()
                .from(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOne(new CandyMapper());
    }
}
