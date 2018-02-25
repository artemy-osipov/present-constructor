package ru.home.shop.utils.db;

import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.assertj.core.api.AbstractLongAssert;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

class ExpectedQueryCountTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestExecution(TestContext testContext) {
        QueryCountHolder.clear();
    }

    @Override
    public void afterTestExecution(TestContext testContext) {
        ExpectedQueryCount expectedQueryCount = testContext.getTestMethod().getAnnotation(ExpectedQueryCount.class);
        QueryCount queryCount = QueryCountHolder.getGrandTotal();

        if (expectedQueryCount != null) {
            for (ExpectedQueryCount.Query query : expectedQueryCount.queries()) {
                long count;
                switch (query.type()) {
                    case INSERT:
                        count = queryCount.getInsert();
                        break;
                    case SELECT:
                        count = queryCount.getSelect();
                        break;
                    case UPDATE:
                        count = queryCount.getUpdate();
                        break;
                    case DELETE:
                        count = queryCount.getDelete();
                        break;
                    default:
                        count = 0;
                }

                AbstractLongAssert<?> countAssert = assertThat(count).as("check %s query count", query.type());

                if (expectedQueryCount.allowLess()) {
                    countAssert.isLessThanOrEqualTo(query.count());
                } else {
                    countAssert.isEqualTo(query.count());
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
