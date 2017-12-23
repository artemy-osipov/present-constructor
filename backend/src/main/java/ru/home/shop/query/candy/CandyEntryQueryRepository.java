package ru.home.shop.query.candy;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

import static ru.home.db.tables.Candy.CANDY;

@Repository
public class CandyEntryQueryRepository {

    private final DSLContext dsl;

    @Autowired
    public CandyEntryQueryRepository(DSLContext dsl) {
        this.dsl = dsl;
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
