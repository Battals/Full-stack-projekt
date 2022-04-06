package com.example.fullstackprojekt.models;

public class Wish {
    private final String name;
    private final String link;

    public Wish(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
