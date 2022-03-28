package com.example.fullstackprojekt.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class Controller {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/rasha")
    public String rasha(){
        return "indexRasha";
    }

    @GetMapping("/battal")
    public String battal(){
        return "indexBattal";
    }

}
