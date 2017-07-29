package ru.home.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.home.shop.*")
public class PresentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentsApplication.class, args);
    }
}

