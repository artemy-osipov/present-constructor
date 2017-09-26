package ru.home.shop.query.candy;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

import static ru.home.db.tables.Candy.CANDY;

@Repository
public class CandyEntryRepository {

    private final DSLContext dsl;

    @Autowired
    public CandyEntryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void add(CandyEntry candy) {
        dsl.insertInto(CANDY)
                .set(CANDY.ID, candy.getId())
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .execute();
    }

    public int remove(UUID id) {
        return dsl.update(CANDY)
                .set(CANDY.ACTIVE, false)
                .where(CANDY.ID.eq(id))
                .execute();
    }

    public int edit(CandyEntry candy) {
        return dsl.update(CANDY)
                .set(CANDY.NAME, candy.getName())
                .set(CANDY.FIRM, candy.getFirm())
                .set(CANDY.PRICE, candy.getPrice())
                .set(CANDY.ORDER, candy.getOrder())
                .where(CANDY.ID.eq(candy.getId()))
                .execute();
    }

    public Collection<CandyEntry> list() {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ACTIVE.eq(true))
                .orderBy(CANDY.ORDER)
                .fetch(new CandyMapper());
    }

    public CandyEntry findById(UUID id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOne(new CandyMapper());
    }
}
