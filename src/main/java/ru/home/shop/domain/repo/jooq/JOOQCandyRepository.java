package ru.home.shop.domain.repo.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.db.tables.records.CandyRecord;
import ru.home.shop.domain.bean.CandyBean;
import ru.home.shop.domain.repo.CandyRepository;
import ru.home.shop.domain.transformer.CandyTransformer;

import java.math.BigDecimal;
import java.util.Collection;

import static ru.home.shop.db.tables.Candy.CANDY;

@Repository
public class JOOQCandyRepository implements CandyRepository {

    private final DSLContext dsl;

    @Autowired
    public JOOQCandyRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public int add(CandyBean candy) {
        CandyRecord record = dsl.insertInto(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.CANDY_ORDER, BigDecimal.valueOf(candy.getOrder()))
                .returning(CANDY.ID)
                .fetchOne();

        candy.setId(record.getId());

        return candy.getId();
    }

    @Override
    public int remove(int id) {
        return dsl.deleteFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .execute();
    }

    @Override
    public int edit(CandyBean candy) {
        return dsl.update(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.CANDY_ORDER, BigDecimal.valueOf(candy.getOrder()))
                .where(CANDY.ID.eq(candy.getId()))
                .execute();
    }

    @Override
    public Collection<CandyBean> findAll() {
        return dsl.selectFrom(CANDY)
                .orderBy(CANDY.CANDY_ORDER)
                .fetch().map(CandyTransformer::transform);
    }

    @Override
    public CandyBean find(int id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOne(CandyTransformer::transform);
    }
}
