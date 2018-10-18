package ru.home.shop.utils.db;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

class AutoFlushTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestExecution(TestContext testContext) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            getEntityManagerFactory(testContext)
                    .map(EntityManagerFactoryUtils::getTransactionalEntityManager)
                    .ifPresent(EntityManager::flush);
        }
    }

    private Optional<EntityManagerFactory> getEntityManagerFactory(TestContext testContext) {
        try {
            return Optional.of(testContext.getApplicationContext().getBean(EntityManagerFactory.class));
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();
        }
    }

    public int getOrder() {
        return 5000;
    }
}
