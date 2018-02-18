package ru.home.shop.service;

import com.github.database.rider.core .AbstractRiderTestContext;
import com.github.database.rider.core.RiderRunner;
import com.github.database.rider.core.RiderTestContext;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;

public class DBRiderTestExecutionListener implements TestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        RiderTestContext riderTestContext = SpringRiderTestContext.create(testContext);

        RiderRunner riderRunner = new RiderRunner();
        riderRunner.setup(riderTestContext);
        riderRunner.runBeforeTest(riderTestContext);
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        RiderTestContext riderTestContext = SpringRiderTestContext.create(testContext);
        RiderRunner riderRunner = new RiderRunner();

        try {
            riderRunner.runAfterTest(riderTestContext);
        } finally {
            riderRunner.teardown(riderTestContext);
        }
    }

    static class SpringRiderTestContext extends AbstractRiderTestContext {

        private final TestContext testContext;

        static SpringRiderTestContext create(TestContext testContext) {
            ConnectionHolder connectionHolder = () -> testContext.getApplicationContext().getBean(DataSource.class).getConnection();
            return new SpringRiderTestContext(DataSetExecutorImpl.instance(DataSetExecutorImpl.DEFAULT_EXECUTOR_ID, connectionHolder), testContext);
        }

        SpringRiderTestContext(DataSetExecutor executor, TestContext testContext) {
            super(executor);
            this.testContext = testContext;
        }

        @Override
        public String getMethodName() {
            return testContext.getTestMethod().getName();
        }

        @Override
        public <T extends Annotation> T getMethodAnnotation(Class<T> clazz) {
            return testContext.getTestMethod().getAnnotation(clazz);
        }

        @Override
        public <T extends Annotation> T getClassAnnotation(Class<T> clazz) {
            return testContext.getTestClass().getAnnotation(clazz);
        }
    }
}
