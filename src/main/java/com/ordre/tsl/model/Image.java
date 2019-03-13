package com.ordre.tsl.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return position == image.position &&
                Objects.equals(name, image.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }
}
