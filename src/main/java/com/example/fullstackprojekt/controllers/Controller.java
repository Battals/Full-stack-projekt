package com.example.fullstackprojekt.controllers;



import com.example.fullstackprojekt.respositories.SQLManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


@org.springframework.stereotype.Controller

public class Controller {
    private String test;
    @GetMapping("/")
    public String index(){
        System.out.println(test);
        return "indexBattal";
    }

    @GetMapping("/rasha")
    public String rasha(){
        test = "rasha";
        return "indexRasha";
    }

    @GetMapping("/login")
    public String battal(){
        test = "battal";
        return "indexBattal";
    }

    @PostMapping("/logging")
    public String logging(WebRequest dataFromForm){
        SQLManager sql = new SQLManager();
        sql.start();
        try{
            //"dataFromForm.getParameter("username")" = String
            System.out.println(dataFromForm.getParameter("username"));
            System.out.println(dataFromForm.getParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
