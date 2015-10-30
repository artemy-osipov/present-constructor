package ru.home.shop;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static ru.home.db.tables.Candy.CANDY;

@Service
public class HelloService {

    private DSLContext dsl;

    @Autowired
    public HelloService(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public void create(int id, String name, String firm, int count) {

        for (int i = 0; i < count; i++)
            dsl.insertInto(CANDY)
                    .set(CANDY.ID, id)
                    .set(CANDY.NAME, name)
                    .set(CANDY.FIRM, firm)
                    .set(CANDY.PRICE, BigDecimal.valueOf(1))
                    .execute();
    }
}
