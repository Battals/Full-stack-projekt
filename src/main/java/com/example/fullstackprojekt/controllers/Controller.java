package com.example.fullstackprojekt.controllers;



import com.example.fullstackprojekt.respositories.SQLManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;


@org.springframework.stereotype.Controller

public class Controller {
    /*
     Session Attributes:
     boolean - 'logged-in'(true/false)
     String - username(username)
     */

    @GetMapping("/")
    public String index(HttpSession session){
        if((boolean) session.getAttribute("logged-in")){
            return "index-logged-in";
        } else {
            return "index";
        }
    }

    @GetMapping("/login")
    public String battal(HttpSession session){
        if(session.getAttribute("username")==null){
            return "redirect:/";
        } else {
            return "indexBattal";
        }
    }

    @GetMapping("/create") //able to create a user
    public String createUser(HttpSession session){
        if(session.getAttribute("username")==null) {
            return "createUser";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/add-wish")//able to input a name and link, which will be added to wishlist as a wish
    public String addWish(HttpSession session){
        if(session.getAttribute("username")==null){
            return "redirect:/";
        } else {
            return "addWish";
        }
    }

    @GetMapping("/edit-wishlist") //Can remove wish, and redirect oneself to /add-wish
    public String editWishlist(HttpSession session){
        if(session.getAttribute("username")==null){
            return "redirect:/";
        } else {
            return "editWishlist";
        }
    }

    @GetMapping("/about") //lav en html med about?
    public String about(){
        return "redirect:/"; //
    }


    //post mapping for user, dataFromForm is input put into string form
    @PostMapping("/logging") //log into an existing user
    public String logging(WebRequest dataFromForm, HttpSession session){
        SQLManager sql = new SQLManager();
        sql.start();
        try{
            if(sql.login(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))){
                session.setAttribute("username", dataFromForm.getParameter("username"));
            } else {
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/creating") //Create a new user
    public String creating(WebRequest dataFromForm, HttpSession session){
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            if(sql.createUser(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))){
                session.setAttribute("username", dataFromForm.getParameter("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/create";
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
