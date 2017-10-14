package ru.home.shop.domain;

import org.axonframework.commandhandling.model.LockingRepository;
import org.axonframework.commandhandling.model.inspection.AnnotatedAggregate;
import org.axonframework.eventhandling.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import ru.home.shop.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;

abstract class SimpleAggregateRepository<T> extends LockingRepository<T, AnnotatedAggregate<T>> {

    private final EventBus eventBus;

    @Autowired
    SimpleAggregateRepository(Class<T> clazz, EventBus eventBus) {
        super(clazz);
        this.eventBus = eventBus;
    }

    @Override
    protected AnnotatedAggregate<T> doCreateNewForLock(Callable<T> factoryMethod) throws Exception {
        return AnnotatedAggregate.initialize(factoryMethod, aggregateModel(), eventBus);
    }

    @Override
    protected AnnotatedAggregate<T> doLoadWithLock(String aggregateIdentifier, Long expectedVersion) {
        Optional<T> aggregateRoot = load(UUID.fromString(aggregateIdentifier));

        if (aggregateRoot.isPresent()) {
            return AnnotatedAggregate.initialize(aggregateRoot.get(), aggregateModel(), eventBus);
        } else {
            throw new EntityNotFoundException();
        }
    }

    protected abstract Optional<T> load(UUID id);

    @Override
    protected void doSaveWithLock(AnnotatedAggregate<T> aggregate) {
        System.out.println("doSaveWithLock");
    }

    @Override
    protected void doDeleteWithLock(AnnotatedAggregate<T> aggregate) {
        System.out.println("doDeleteWithLock");
    }
}
