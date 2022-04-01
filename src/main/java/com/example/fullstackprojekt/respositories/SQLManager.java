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

    public void login(String name, String password){ //User can log in, and program registers credentials
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

    public void logout(){ //logout function
        user = null;
        wishlist = null;
    }

    //User menu
    public ArrayList<Wish> getWishlist(){ //Creates an ArrayList containing Wish objects
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

    public void addWish(String name, String link){ //Adds a wish to current users wishlist
        try {
            sqlString = "INSERT INTO " + wishlist + "(name, link, date)" +
                    " VALUES(" + name + ", " + link + ", " + LocalDate.now() + ")";
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWish(Wish wish){ //Deletes a wish from current users wishlist
        try {
            sqlString = "DELETE FROM " + wishlist + " WHERE name = " + wish.getName() + ")";
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Admin Menu
    private void runOnFirstStartUp(){
    }
    public void createUser(String username, String password){ //Creates a new user
        try{
            try {
                stmt.executeUpdate("DROP TABLE wishlist_" + username);
            } catch (SQLException e) {
                //no prior wishlists for username
            }
            stmt.executeUpdate("INSERT INTO users VALUES('" + username + "', '" + password + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(String username){ //Deletes user and their wishlist
        try {
            stmt.executeUpdate("DROP TABLE wishlist_" + username);
            stmt.executeUpdate("DELETE FROM users WHERE name = '" + username + "'");
            System.out.println("deleted the user (" + username + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean userExists(String username){ //returns a boolean whether username is used or not
        boolean userExists = false;
        try {
            stmt.executeQuery("SELECT * FROM users");
            while(rs.next()){
                if(rs.getString("name").equalsIgnoreCase(username)){
                    userExists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    //System Management
    private void establishConnection(){ //Creates a connection to the sql database
        String url ="jdbc:mysql://wishlist1.mysql.database.azure.com:3306/wishlist1?useSSL=true&requireSSL=false";
        String rootName = "admin1@wishlist1";
        String password = "Hej12345";
        //Process
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
