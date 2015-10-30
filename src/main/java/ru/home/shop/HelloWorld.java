package ru.home.shop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/welcome")
    public String helloWorld() {
        return  "Hello World!";
    }
}
