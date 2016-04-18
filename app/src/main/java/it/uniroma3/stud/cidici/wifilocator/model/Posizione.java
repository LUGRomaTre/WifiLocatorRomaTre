package it.uniroma3.stud.cidici.wifilocator.model;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 18/04/16.
 */
public class Posizione {

    private double x;
    private double y;

    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
