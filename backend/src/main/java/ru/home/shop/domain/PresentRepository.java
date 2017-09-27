package ru.home.shop.domain;

import org.axonframework.commandhandling.model.LockingRepository;
import org.axonframework.commandhandling.model.inspection.AnnotatedAggregate;
import org.axonframework.eventhandling.EventBus;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.home.shop.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

import static ru.home.db.Tables.PRESENT;
import static ru.home.db.Tables.PRESENT_ITEM;

@Repository
public class PresentRepository extends LockingRepository<Present, AnnotatedAggregate<Present>> {

    private final EventBus eventBus;
    private final DSLContext dsl;

    @Autowired
    public PresentRepository(EventBus eventBus, DSLContext dsl) {
        super(Present.class);
        this.eventBus = eventBus;
        this.dsl = dsl;
    }

    @Override
    protected AnnotatedAggregate<Present> doCreateNewForLock(Callable<Present> factoryMethod) throws Exception {
        return AnnotatedAggregate.initialize(factoryMethod, aggregateModel(), eventBus);
    }

    @Override
    protected AnnotatedAggregate<Present> doLoadWithLock(String aggregateIdentifier, Long expectedVersion) {
        Optional<Present> aggregateRoot = load(UUID.fromString(aggregateIdentifier));

        if (aggregateRoot.isPresent()) {
            return AnnotatedAggregate.initialize(aggregateRoot.get(), aggregateModel(), eventBus);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Optional<Present> load(UUID id) {
        return dsl.selectFrom(PRESENT)
                .where(PRESENT.ID.eq(id))
                .fetchOptional()
                .map(r -> new Present(r.getId(), r.getName(), r.getPrice(), r.getDate(), listPresentItem(r.getId())));
    }

    private List<PresentItem> listPresentItem(UUID present) {
        return dsl.selectFrom(PRESENT_ITEM)
                .where(PRESENT_ITEM.PRESENT.eq(present))
                .fetch()
                .map(r -> new PresentItem(r.getCandy(), r.getCount()));
    }

    @Override
    protected void doSaveWithLock(AnnotatedAggregate<Present> aggregate) {
        System.out.println("doSaveWithLock");
    }

    @Override
    protected void doDeleteWithLock(AnnotatedAggregate<Present> aggregate) {
        System.out.println("doDeleteWithLock");
    }
}
