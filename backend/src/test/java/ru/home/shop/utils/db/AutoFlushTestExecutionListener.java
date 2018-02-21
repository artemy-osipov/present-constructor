package ru.home.shop.utils.db;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManagerFactory;

class AutoFlushTestExecutionListener implements TestExecutionListener {

    @Override
    public void afterTestExecution(TestContext testContext) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            EntityManagerFactory entityManagerFactory = testContext.getApplicationContext().getBean(EntityManagerFactory.class);

            EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory).flush();
        }
    }
}
