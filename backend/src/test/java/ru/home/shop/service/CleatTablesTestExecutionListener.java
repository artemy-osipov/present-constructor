package ru.home.shop.service;

import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class CleatTablesTestExecutionListener implements TestExecutionListener {

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        DSLContext dsl = testContext.getApplicationContext().getBean(DSLContext.class);
        CleanTables cleanTables = testContext.getTestClass().getAnnotation(CleanTables.class);

        if (cleanTables != null) {
            Class<? extends Table>[] classes = cleanTables.value();
            for (Class<? extends Table> tableClass : classes) {
                Table<?> table = tableClass.newInstance();
                dsl.deleteFrom(table).execute();
            }
        }
    }
}
