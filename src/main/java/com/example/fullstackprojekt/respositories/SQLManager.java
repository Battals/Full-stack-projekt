package com.example.fullstackprojekt.respositories;

import com.example.fullstackprojekt.models.Wish;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLManager {
    //SQL Attributes
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private String sqlString;

    //User Attributes
    private String user;
    private String wishlist;

    /*
    Classes:
    Wish - name, link, date

    Tables:
    users - name, password
    wishlist_(author) - name, link, date
    */


    //Tools
    public void start(){
        establishConnection();
    }

    public void login(String name, String password){
        try{
            rs = stmt.executeQuery("SELECT * FROM users ORDER BY name");
            while(rs.next()){
                if(password.equalsIgnoreCase(rs.getString("password"))){
                    user = rs.getString("name");
                    wishlist = "wishlist_" + rs.getString("name");
                    System.out.println("user logged in: " + rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public void logout(){
        user = null;
        wishlist = null;
    }

    //User menu
    public ArrayList<Wish> getWishlist(){
        ArrayList<Wish> wishes = new ArrayList<>();
        try {
            sqlString = "SELECT * FROM " + wishlist + " ORDER BY date";
            rs = stmt.executeQuery(sqlString);
            while(rs.next()){
                wishes.add(new Wish(rs.getString("name"), rs.getString("link"), rs.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishes;
    }

    public void addWish(String name, String link){
        try {
            sqlString = "INSERT INTO " + wishlist + "(name, link, date)" +
                    " VALUES(" + name + ", " + link + ", " + LocalDate.now() + ")";
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWish(Wish wish){
        try {
            sqlString = "DELETE FROM " + wishlist + " WHERE name = " + wish.getName() + ")";
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Admin Menu
    private void deleteUser(String username){
        try {
            stmt.executeUpdate("DROP TABLE wishlist_" + username);
            stmt.executeUpdate("DELETE FROM users WHERE name = " + username);
            System.out.println("deleted the user (" + username + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //System Management
    private void establishConnection(){
        //Database Credentials
        String url ="jdbc:mysql://wishlist1.mysql.database.azure.com:3306/wishlist1?useSSL=true&requireSSL=false";
        String rootName = "admin1@wishlist1";
        String password = "Testtest1";
        try {
            //Define URL of the database
            System.out.println("url defined");

            //Get connection to database
            con = DriverManager.getConnection(url, rootName, password);
            System.out.println("connection made");

            //Display the URL and connection information
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
