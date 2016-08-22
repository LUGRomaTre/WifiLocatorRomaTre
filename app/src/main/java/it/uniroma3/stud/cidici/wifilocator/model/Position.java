package it.uniroma3.stud.cidici.wifilocator.model;

import java.util.Date;

public class Position {

    private final double x;
    private final double y;
    private final long timestamp;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        timestamp = (new Date()).getTime();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
