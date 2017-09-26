package ru.home.shop.domain;

import org.axonframework.commandhandling.model.LockingRepository;
import org.axonframework.commandhandling.model.inspection.AnnotatedAggregate;
import org.axonframework.eventhandling.EventBus;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

import static ru.home.db.Tables.CANDY;

@Repository
public class CandyRepository extends LockingRepository<Candy, AnnotatedAggregate<Candy>> {

    private final EventBus eventBus;
    private final DSLContext dsl;

    @Autowired
    public CandyRepository(EventBus eventBus, DSLContext dsl) {
        super(Candy.class);
        this.eventBus = eventBus;
        this.dsl = dsl;
    }

    @Override
    protected AnnotatedAggregate<Candy> doCreateNewForLock(Callable<Candy> factoryMethod) throws Exception {
        return AnnotatedAggregate.initialize(factoryMethod, aggregateModel(), eventBus);
    }

    @Override
    protected AnnotatedAggregate<Candy> doLoadWithLock(String aggregateIdentifier, Long expectedVersion) {
        Optional<Candy> aggregateRoot = load(UUID.fromString(aggregateIdentifier));

        if (aggregateRoot.isPresent()) {
            return AnnotatedAggregate.initialize(aggregateRoot.get(), aggregateModel(), eventBus);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Optional<Candy> load(UUID id) {
        return dsl.selectFrom(CANDY)
                .where(CANDY.ID.eq(id))
                .fetchOptional()
                .map(r -> new Candy(r.getId(), r.getName(), r.getFirm(), r.getOrder(), r.getPrice(), r.getActive()));
    }

    @Override
    protected void doSaveWithLock(AnnotatedAggregate<Candy> aggregate) {
        System.out.println("doSaveWithLock");
    }

    @Override
    protected void doDeleteWithLock(AnnotatedAggregate<Candy> aggregate) {
        System.out.println("doDeleteWithLock");
    }
}
