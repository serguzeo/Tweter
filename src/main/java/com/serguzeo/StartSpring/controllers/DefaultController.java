package com.serguzeo.StartSpring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DefaultController {

    @GetMapping(value = {"/{path:^(?!home$|404$|login$|register$).*$}"})
    public String redirect(@PathVariable String path) {
        return "profile";
    }
}
