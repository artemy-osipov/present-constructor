package ru.home.shop.service.query.candy;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static ru.home.db.tables.Candy.CANDY;

@Repository
@RequiredArgsConstructor
public class CandyQueryRepository {

    private final DSLContext dsl;
    private final CandyMapper candyMapper;

    public Collection<CandyQuery> list() {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ACTIVE.eq(true))
                .orderBy(CANDY.ORDER)
                .fetch(candyMapper);
    }

    public Optional<CandyQuery> findById(UUID id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOptional(candyMapper);
    }
}
