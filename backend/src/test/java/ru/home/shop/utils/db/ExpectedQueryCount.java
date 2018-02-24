package ru.home.shop.utils.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectedQueryCount {

    Query[] queries();

    boolean allowLess() default false;

    @interface Query {

        Type type();

        int count();
    }

    enum Type {
        INSERT, SELECT, UPDATE, DELETE
    }
}
