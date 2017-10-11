package ru.home.shop.domain;

import org.axonframework.eventhandling.EventBus;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static ru.home.db.Tables.PRESENT;

@Repository
public class PresentRepository extends SimpleAggregateRepository<Present> {

    private final DSLContext dsl;

    @Autowired
    public PresentRepository(EventBus eventBus, DSLContext dsl) {
        super(Present.class, eventBus);
        this.dsl = dsl;
    }

    protected Optional<Present> load(UUID id) {
        return dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOptional()
                .map(r -> new Present(r.getId()));
    }
}
