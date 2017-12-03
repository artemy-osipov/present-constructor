package ru.home.shop.domain;

import org.axonframework.eventhandling.EventBus;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static ru.home.db.Tables.CANDY;

@Repository
public class CandyRepository extends SimpleAggregateRepository<Candy> {

    private final DSLContext dsl;

    @Autowired
    public CandyRepository(EventBus eventBus, DSLContext dsl) {
        super(Candy.class, eventBus);
        this.dsl = dsl;
    }

    protected Optional<Candy> load(UUID id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id).and(CANDY.ACTIVE.eq(true)))
                .fetchOptional()
                .map(r -> new Candy(r.getId()));
    }
}
