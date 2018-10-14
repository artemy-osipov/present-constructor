/*
 * This file is generated by jOOQ.
*/
package ru.home.db;


import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import ru.home.db.tables.Candy;
import ru.home.db.tables.FlywaySchemaHistory;
import ru.home.db.tables.Present;
import ru.home.db.tables.PresentItem;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5",
        "schema version:PUBLIC_1.5.0"
    },
    date = "2018-10-14T18:23:22.262Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PRIMARY_KEY_5 = Indexes0.PRIMARY_KEY_5;
    public static final Index LIST = Indexes0.LIST;
    public static final Index PRIMARY_KEY_6 = Indexes0.PRIMARY_KEY_6;
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Indexes0.FLYWAY_SCHEMA_HISTORY_S_IDX;
    public static final Index PRIMARY_KEY_E = Indexes0.PRIMARY_KEY_E;
    public static final Index CONSTRAINT_INDEX_2 = Indexes0.CONSTRAINT_INDEX_2;
    public static final Index PRIMARY_KEY_2 = Indexes0.PRIMARY_KEY_2;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PRIMARY_KEY_5 = Internal.createIndex("PRIMARY_KEY_5", Candy.CANDY, new OrderField[] { Candy.CANDY.ID }, true);
        public static Index LIST = Internal.createIndex("list", Candy.CANDY, new OrderField[] { Candy.CANDY.ACTIVE, Candy.CANDY.ORDER }, false);
        public static Index PRIMARY_KEY_6 = Internal.createIndex("PRIMARY_KEY_6", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
        public static Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex("flyway_schema_history_s_idx", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
        public static Index PRIMARY_KEY_E = Internal.createIndex("PRIMARY_KEY_E", Present.PRESENT, new OrderField[] { Present.PRESENT.ID }, true);
        public static Index CONSTRAINT_INDEX_2 = Internal.createIndex("CONSTRAINT_INDEX_2", PresentItem.PRESENT_ITEM, new OrderField[] { PresentItem.PRESENT_ITEM.CANDY_ID }, false);
        public static Index PRIMARY_KEY_2 = Internal.createIndex("PRIMARY_KEY_2", PresentItem.PRESENT_ITEM, new OrderField[] { PresentItem.PRESENT_ITEM.PRESENT_ID, PresentItem.PRESENT_ITEM.CANDY_ID }, true);
    }
}
