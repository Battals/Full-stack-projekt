package com.example.fullstackprojekt.services;

public class ValidationService {

    public boolean isEmailValid(String email){
        if (email.contains("@") && email.contains(".")
                && email.length()>5){
            return true;
        }
        return false;
    }
    public void validateLogin(){
    }
    public void validateNewLogin(){
    }

}
