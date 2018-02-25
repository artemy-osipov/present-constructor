package ru.home.shop.service.query.candy;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static ru.home.db.tables.Candy.CANDY;

@Repository
public class CandyQueryRepository {

    private final DSLContext dsl;

    @Autowired
    public CandyQueryRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Collection<CandyQuery> list() {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ACTIVE.eq(true))
                .orderBy(CANDY.ORDER)
                .fetch(new CandyMapper());
    }

    public Optional<CandyQuery> findById(UUID id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOptional(new CandyMapper());
    }
}
