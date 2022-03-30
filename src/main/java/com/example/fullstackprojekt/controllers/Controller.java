package com.example.fullstackprojekt.controllers;



import org.springframework.web.bind.annotation.GetMapping;



@org.springframework.stereotype.Controller

public class Controller {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/rasha")
    public String rasha(){
        return "indexRasha";
    }

    @GetMapping("/login")
    public String battal(){
        return "indexBattal";
    }

}
