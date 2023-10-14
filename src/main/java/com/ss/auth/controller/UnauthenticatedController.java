package com.ss.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnauthenticatedController {
    @GetMapping("/public")
    public String sayHello(){
        return "Hello";
    }
    @GetMapping("/")
    public String welcome(){
        return "Hello";
    }
}
