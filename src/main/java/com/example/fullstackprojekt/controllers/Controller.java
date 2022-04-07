package com.example.fullstackprojekt.controllers;


import com.example.fullstackprojekt.models.Wish;
import com.example.fullstackprojekt.respositories.SQLManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@org.springframework.stereotype.Controller

public class Controller {
    /*
     Session Attributes:
     boolean - 'logged-in'(true/false)
     String - username(username)
     */

    @GetMapping("/")
    public String index(HttpSession session) {
        try {
            if (session.getAttribute("logged-in") != null) {
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
    public String battal(HttpSession session) {
        if (!(Boolean) session.getAttribute("logged-in")) {
            return "indexBattal";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/create") //able to create a user
    public String createUser(HttpSession session) {
        if (!(Boolean) session.getAttribute("logged-in")) {
            return "createUser";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/add-wish")//able to input a name and link, which will be added to wishlist as a wish
    public String addWish(HttpSession session) {
        if (!(Boolean) session.getAttribute("logged-in")) {
            return "addWish";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/account")
    public String loggedIn(HttpSession session) {
        if (!(Boolean) session.getAttribute("logged-in")) {
            return "index-logged-in";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/view-wishlist")
    public String viewWishlist(HttpSession session){
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            //Laver arraylist med brugerens ønsker
            ArrayList<Wish> wishes = sql.getWishlist((String) session.getAttribute("username"));

            //Laver en String med brugerens ønsker fra arraylisten
            //wish.getName() = String med navnet
            //wish.getLink() = String med linket
            String list = "List of " + (String) session.getAttribute("username") + " wishlist:";
            for (Wish wish : wishes) {
                list += "\n" + wish.getName() + ":  " + wish.getLink();
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/account";
    }

    @GetMapping("/edit-wishlist") //Can remove wish, and redirect oneself to /add-wish
    public String editWishlist(HttpSession session) {
        if (!(Boolean) session.getAttribute("logged-in")) {
            return "redirect:/";
        } else {
            return "editWishlist";
        }
    }

    @GetMapping("/delete-wish")
    public String deleteWish(HttpSession session) {
        SQLManager sql = new SQLManager();
        sql.start();
        //sql.deleteWish("Navnet på ønsket der skal slettes", (String) session.getAttribute("username"));
        return "redirect:/";
    }

    @GetMapping("/about") //lav en html med about?
    public String about() {
        return "about"; //
    }

    //post mapping for user, dataFromForm is input put into string form
    @PostMapping("/logging") //log into an existing user
    public String logging(WebRequest dataFromForm, HttpSession session) {
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            if (sql.login(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))) {
                //successful login
                session.setAttribute("username", dataFromForm.getParameter("username"));
                return "redirect:/account";
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
    public String creating(WebRequest dataFromForm, HttpSession session) {
        SQLManager sql = new SQLManager();
        sql.start();
        try {
            if (sql.createUser(dataFromForm.getParameter("username"), dataFromForm.getParameter("password"))) {
                //logged in after creating user
                System.out.println("user created");
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

    @PostMapping("/adding") //Add wish to the wishlis
    public String adding(WebRequest dataFromForm, HttpSession session) {
        SQLManager sqlManager = new SQLManager();
        sqlManager.start();
        try {
            sqlManager.addWish(dataFromForm.getParameter("wish_name"),
                    dataFromForm.getParameter("wish_link"),
                    (String) session.getAttribute("username"));
        } catch (Exception ignored) {

        }
        return "redirect:/add-wish";
    }

    @PostMapping("/removing") //Remove wish from the wishlist
    public String removing(WebRequest dataFromForm, HttpSession session) {
        SQLManager sqlManager = new SQLManager();
        sqlManager.deleteWish(dataFromForm.getParameter("wish-name"),
                (String) session.getAttribute("username"));
        return "redirect:/remove-wish";
    }

    //GetMapping, send string til html
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public ModelAndView messages(HttpSession session) {
        SQLManager sql = new SQLManager();
        sql.start();
        ModelAndView mav = new ModelAndView("message/list");
        mav.addObject("messages", sql.getWishlistString((String) session.getAttribute("username")));
        return mav;
    }
}
