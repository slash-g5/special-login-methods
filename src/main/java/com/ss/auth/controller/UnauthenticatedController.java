package com.ss.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.awt.*;

@RestController
public class UnauthenticatedController {
    @GetMapping(value = "/public", produces = MediaType.TEXT_HTML_VALUE)
    public String sayHello(){
        return "<p>Hello<p>";
    }
    @GetMapping("/")
    public String welcome(){
        return "Hello";
    }
}
