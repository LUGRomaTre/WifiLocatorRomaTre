package it.uniroma3.stud.cidici.wifilocator.model.shapes;

import it.uniroma3.stud.cidici.wifilocator.model.Position;

public class Segment {
    private final Position position0;
    private final Position position1;

    public Segment(Position position0, Position position1) {
        this.position0 = position0;
        this.position1 = position1;
    }

    public Position getPuntoMedio() {
        double xMedio = (position0.getX() + position1.getX()) / 2;
        double yMedio = (position0.getY() + position1.getY()) / 2;
        return new Position(xMedio, yMedio);
    }
}
