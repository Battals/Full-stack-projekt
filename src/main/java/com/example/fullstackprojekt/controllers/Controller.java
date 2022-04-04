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
        try {
            if(session.getAttribute("logged-in")!=null){
                //nice ig
            } else {
                session.setAttribute("logged-in", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/login")
    public String battal(HttpSession session){
        if(!(Boolean) session.getAttribute("logged-in")){
            return "indexBattal";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/create") //able to create a user
    public String createUser(HttpSession session){
        if(!(Boolean) session.getAttribute("logged-in")) {
            return "createUser";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/add-wish")//able to input a name and link, which will be added to wishlist as a wish
    public String addWish(HttpSession session){
        if(!(Boolean) session.getAttribute("logged-in")){
            return "redirect:/";
        } else {
            return "addWish";
        }
    }

    @GetMapping("/edit-wishlist") //Can remove wish, and redirect oneself to /add-wish
    public String editWishlist(HttpSession session){
        if(!(Boolean) session.getAttribute("logged-in")){
            return "redirect:/";
        } else {
            return "editWishlist";
        }
    }

    @GetMapping("/about") //lav en html med about?
    public String about(){
        return "about"; //
    }


    //post mapping for user, dataFromForm is input put into string form
    @PostMapping("/logging") //log into an existing user
    public String logging(WebRequest dataFromForm, HttpSession session){
        SQLManager sql = new SQLManager();
        sql.start();
        try{
            if(sql.login(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))){
                //successful login
                session.setAttribute("username", dataFromForm.getParameter("username"));
                return "redirect:/";
            } else {
                //invalid login
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @PostMapping("/creating") //Create a new user
    public String creating(WebRequest dataFromForm, HttpSession session){
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            if(sql.createUser(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))){
                //logged in after creating user
                session.setAttribute("username", dataFromForm.getParameter("username"));
                return "redirect:/";
            } else {
                return "redirect:/create";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/create";
    }

    @PostMapping("/adding") //Add wish to the wishlist
    public String adding(WebRequest dataFromForm, HttpSession session){
        SQLManager sqlManager = new SQLManager();
        sqlManager.addWish(dataFromForm.getParameter("wish-name"),
                dataFromForm.getParameter("wish-link"),
                (String) session.getAttribute("username"));
        return "redirect:/add-wish";
    }

    @PostMapping("/removing") //Remove wish from the wishlist
    public String removing(WebRequest dataFromForm, HttpSession session){
        SQLManager sqlManager = new SQLManager();
        sqlManager.deleteWish(dataFromForm.getParameter("wish-name"),
                (String) session.getAttribute("username"));
        return "redirect:/remove-wish";
    }
}
