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

    @GetMapping("/create") //able to create a user
    public String createUser(){
        return "createUser";
    }

    @GetMapping("/add-wish")//able to input a name and link, which will be added to wishlist as a wish
    public String addWish(){
        return "addWish";
    }

    @GetMapping("/edit-wishlist") //Can remove wish, and redirect oneself to /add-wish
    public String editWishlist(){
        return "editWishlist";
    }

    @GetMapping("/about") //lav en html med about?
    public String about(){
        return "redirect:/";
    }


    //post mapping for user, dataFromForm is input put into string form
    @PostMapping("/logging") //log into an existing user
    public String logging(WebRequest dataFromForm){
        SQLManager sql = new SQLManager();
        sql.start();
        try{
            //"dataFromForm.getParameter("username")" = String
            sql.login(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"));
            System.out.println(dataFromForm.getParameter("username"));
            System.out.println(dataFromForm.getParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/creating") //Create a new user
    public String creating(WebRequest dataFromForm){
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            //Opret bruger, parametre til oprettelse af ny bruger til databasen
            sql.createUser(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"));
            System.out.println(dataFromForm.getParameter("username"));
            //Evt. kan email være inkluderet i databasen
            System.out.println(dataFromForm.getParameter("email"));
            System.out.println(dataFromForm.getParameter("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/adding") //Add wish to the wishlist
    public String adding(){
        return "redirect:/add-wish";
    }

    @PostMapping("/removing") //Remove wish from the wishlist
    public String removing(){
        return "redirect:/remove-wish";
    }
}
