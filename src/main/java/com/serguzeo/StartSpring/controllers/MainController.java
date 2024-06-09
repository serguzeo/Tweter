//package com.serguzeo.StartSpring.controllers;
//
//import com.serguzeo.StartSpring.models.Message;
//import com.serguzeo.StartSpring.services.I.IMessageService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@AllArgsConstructor
//@RequestMapping("/")
//public class MainController {
//    private IMessageService messageService;
//
//    @GetMapping
//    public String index(Model model) {
//        Iterable<Message> messages = messageService.findAll();
//        return "home";
//    }
//}
