package com.example.fullstackprojekt.models;

import java.util.ArrayList;

public class WishList {
    private ArrayList<Wish> wishlist;
    private final String author;

    public WishList(ArrayList<Wish> wishlist, String author) {
        this.author = author;
        this.wishlist = wishlist;
    }

    public String getAuthor() {
        return author;
    }

    public ArrayList<Wish> getWishlist() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "author='" + author + '\'' +
                ", wishlist=" + wishlist +
                '}';
    }
}
