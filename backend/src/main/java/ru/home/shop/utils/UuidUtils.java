package ru.home.shop.utils;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

public class UuidUtils {

    private static TimeBasedGenerator generator = Generators.timeBasedGenerator();

    public static UUID newUUID() {
        return generator.generate();
    }
}
