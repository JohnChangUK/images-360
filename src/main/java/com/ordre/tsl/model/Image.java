package com.ordre.tsl.model;

public class Image {

    private final String name;
    private final int position;

    public Image(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
