package ru.home.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PresentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentsApplication.class, args);
    }
}

