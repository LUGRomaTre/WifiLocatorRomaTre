package it.uniroma3.stud.cidici.wifilocator.model;

public class Triangolo {
    private final Posizione v1;
    private final Posizione v2;
    private final Posizione v3;

    public Triangolo(Posizione v1, Posizione v2, Posizione v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Posizione getBaricentro() {
        return new Posizione((v1.getX() + v2.getX() + v3.getX()) / 3, (v1.getY() + v2.getY() + v3.getY()) / 3);
    }

}
