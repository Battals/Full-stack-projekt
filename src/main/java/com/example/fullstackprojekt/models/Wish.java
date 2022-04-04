package com.example.fullstackprojekt.models;

public class Wish {
    private final String name;
    private final String link;
    private final String date;

    public Wish(String name, String link, String date) {
        this.name = name;
        this.link = link;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
