package com.example.fullstackprojekt.services;

public class ValidationService {

    public boolean isEmailValid(String email){
        return email.contains("@") && email.contains(".")
                && email.length() > 5;
    }
    public void validateLogin(){
    }
    public void validateNewLogin(){
    }

}
