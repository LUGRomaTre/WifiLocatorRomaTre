package it.uniroma3.stud.cidici.wifilocator.model.shapes;

import it.uniroma3.stud.cidici.wifilocator.model.Position;

public class Triangle {
    private final Position v1;
    private final Position v2;
    private final Position v3;

    public Triangle(Position v1, Position v2, Position v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Position getBaricentro() {
        return new Position((v1.getX() + v2.getX() + v3.getX()) / 3, (v1.getY() + v2.getY() + v3.getY()) / 3);
    }

}
