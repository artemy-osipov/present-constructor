package ru.home.shop.utils.db;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

class AutoFlushTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestExecution(TestContext testContext) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            EntityManagerFactory entityManagerFactory = testContext.getApplicationContext().getBean(EntityManagerFactory.class);
            EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
            entityManager.flush();
        }
    }

    @Override
    public int getOrder() {
        return 5000;
    }
}
