package com.example.fullstackprojekt.respositories;

import com.example.fullstackprojekt.models.Wish;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SQLManager {
    private Statement stmt;
    private ResultSet rs;
    private String sqlString;

    /*
    Classes:
    Wish - name, link, date

    Tables:
    users - name, password
    wishlist_(author) - name, link, date
    */


    //Tools
    public void start() {
        establishConnection();
    }

    public boolean login(String username, String password) { //User can log in, and program registers credentials
        try {
            rs = stmt.executeQuery("SELECT * FROM users ORDER BY username");
            while (rs.next()) {
                if (rs.getString("username").equals(username)) {
                    System.out.println("user found");
                    if (rs.getString("password").equals(password)) {
                        System.out.println("password matches");
                        return true;
                    } else {
                        System.out.println("invalid password");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error?");
        }
        return false;
    }

    public void logout() { //logout function
    }

    //User menu
    public ArrayList<Wish> getWishlist(String username) { //Creates an ArrayList containing Wish objects
        ArrayList<Wish> wishes = new ArrayList<>();
        try {
            sqlString = "SELECT * FROM wishlist_" + username + " ORDER BY wish_name";
            rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                wishes.add(new Wish(rs.getString("wish_name"), rs.getString("wish_link")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishes;
    }
    public String getWishlistString(String username){
        ArrayList<Wish> wishes = getWishlist(username);
        String list = "List of " + username + " wishlist:";
        for (Wish wish : wishes) {
            list += "\n" + wish.getName() + ":  " + wish.getLink();
        }
        return list;
    }

    public void addWish(String name, String link, String username) { //Adds a wish to current users wishlist
        try {
            sqlString = "INSERT INTO wishlist_" + username + "(wish_name, wish_link)" +
                    " VALUES('" + name + "', '" + link + "')";
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWish(String name, String username) { //Deletes a wish from current users wishlist
        try {
            sqlString = "DELETE FROM wishlist_" + username + " WHERE wish_name = '" + name + "'";
            System.out.println(sqlString);
            stmt.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Admin Menu
    private void runOnFirstStartUp(){
    }

    public boolean createUser(String username, String password) { //Creates a new user
        try {
            try {
                stmt.executeUpdate("DROP TABLE wishlist_" + username);
                System.out.println("deleting old list=");
            } catch (SQLException e) {
                //no prior wishlists for username
                System.out.println("new user");
            }
            stmt.executeUpdate("INSERT INTO users VALUES('" + username + "', '" + password + "')");
            createWishlist(username);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void createWishlist(String username){
        try {
            stmt.executeUpdate("CREATE TABLE wishlist_" + username + "(wish_name varchar(45), wish_link varchar(2000))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(String username) { //Deletes user and their wishlist
        try {
            stmt.executeUpdate("DROP TABLE wishlist_" + username);
            stmt.executeUpdate("DELETE FROM users WHERE name = '" + username + "'");
            System.out.println("deleted the user (" + username + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean userExists(String username) { //returns a boolean whether username is used or not
        boolean userExists = false;
        try {
            stmt.executeQuery("SELECT * FROM users ORDER BY username");
            while (rs.next()) {
                if (rs.getString("username").equalsIgnoreCase(username)) {
                    userExists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }
    public void resetDatabase(){ //Deletes all data and resets database
       ArrayList<String> users = new ArrayList<>();
       try {
           rs = stmt.executeQuery("SELECT * FROM users ORDER BY username");
           while (rs.next()) {
               users.add(rs.getString("username"));
               System.out.println("found user - " + rs.getString("username"));
           }
           for (int i = 0; i < users.size(); i++) {
               try {
                   System.out.println("DROP TABLE wishlist_" + users.get(i));
                   stmt.executeUpdate("DROP TABLE wishlist_" + users.get(i));
               } catch (SQLException e) {
                   System.out.println(users.get(i) + " has no database");
               }
               System.out.println(users.get(i) + " wishlist deleted");
               System.out.println("DELETE FROM users WHERE username = '" + users.get(i) + "'");
               stmt.executeUpdate("DELETE FROM users WHERE username = '" + users.get(i) + "'");
               System.out.println(users.get(i) + "deleted");
           }
       } catch (SQLException e) {
           System.out.println("rpi");
           e.printStackTrace();
       }
    }

    //System Management
    private void establishConnection() { //Creates a connection to the sql database
        String url ="jdbc:mysql://onskelisten.mysql.database.azure.com:3306/ønskelisten?verifyServerCertificate=true&useSSL=true&requireSSL=true";
        //Process
        try {
            //Define URL of the database
            System.out.println("url defined");

            //Get connection to database
            //SQL Attributes
            Connection con = DriverManager.getConnection(url, "admin1@onskelisten", "Hej12345");
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
