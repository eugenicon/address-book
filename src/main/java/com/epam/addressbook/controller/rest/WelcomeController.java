package com.epam.addressbook.controller.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    private final String string;

    public WelcomeController(@Value("${WELCOME_MESSAGE}") String string) {
        this.string = string;
    }

    @GetMapping("/")
    public String greeting() {
        return string;
    }
}
