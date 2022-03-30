package com.example.fullstackprojekt.controllers;



import org.springframework.web.bind.annotation.GetMapping;



@org.springframework.stereotype.Controller

public class Controller {
    private String test;
    @GetMapping("/")
    public String index(){
        System.out.println(test);
        return "index";
    }

    @GetMapping("/rasha")
    public String rasha(){
        test = "rasha";
        return "indexRasha";
    }

    @GetMapping("/test")
    public String battal(){
        test = "battal";
        return "indexBattal";
    }

}
