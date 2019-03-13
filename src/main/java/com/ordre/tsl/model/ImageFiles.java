package com.ordre.tsl.model;

import java.util.Objects;

public class ImageFiles {

    private final Image front;
    private final Image back;
    private final Image leftSide;
    private final Image rightSide;

    public ImageFiles(Image front, Image back, Image leftSide, Image rightSide) {
        this.front = front;
        this.back = back;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public Image getFront() {
        return front;
    }

    public Image getBack() {
        return back;
    }

    public Image getLeftSide() {
        return leftSide;
    }

    public Image getRightSide() {
        return rightSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageFiles that = (ImageFiles) o;
        return Objects.equals(front, that.front) &&
                Objects.equals(back, that.back) &&
                Objects.equals(leftSide, that.leftSide) &&
                Objects.equals(rightSide, that.rightSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(front, back, leftSide, rightSide);
    }
}
