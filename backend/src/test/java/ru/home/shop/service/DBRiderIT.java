package ru.home.shop.service;

import com.github.database.rider.core.DBUnitRule;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class DBRiderIT {

    private static DSLContext dsl;
    private static Table<?>[] cleanTable;
    private boolean disableForeignCheck;

    @Autowired
    private DataSource dataSource;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> dataSource.getConnection());

    protected void disableForeignCheck() {
        this.disableForeignCheck = true;
    }

    protected void cleanDataAfterClass(Table<?>... table) {
        DBRiderIT.cleanTable = table;
    }

    @Before
    public void setup() {
        if (disableForeignCheck) {
            dsl.query("SET foreign_key_checks = 0;").execute();
        }
    }

    @After
    public void teardown() {
        if (disableForeignCheck) {
            dsl.query("SET foreign_key_checks = 1;").execute();
        }
    }

    @AfterClass
    public static void deleteData() {
        if (cleanTable != null) {
            for (Table<?> t : cleanTable) {
                dsl.deleteFrom(t).execute();
            }
        }
    }

    @Autowired
    private void initStaticFields(DSLContext dsl) {
        DBRiderIT.dsl = dsl;
    }
}