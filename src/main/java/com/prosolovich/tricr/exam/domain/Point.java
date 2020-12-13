package com.prosolovich.tricr.exam.domain;

public class Point {
    private final double x;
    private final double y;
    private final int r;
    private final boolean isEntry;

    public Point(double x, double y, int r, boolean isEntry) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isEntry = isEntry;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public boolean isEntry() {
        return isEntry;
    }
}
