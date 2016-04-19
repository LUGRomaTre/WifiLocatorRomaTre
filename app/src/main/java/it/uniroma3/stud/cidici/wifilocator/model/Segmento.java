package it.uniroma3.stud.cidici.wifilocator.model;

public class Segmento {
    private final Posizione posizione0;
    private final Posizione posizione1;

    public Segmento(Posizione posizione0, Posizione posizione1) {
        this.posizione0 = posizione0;
        this.posizione1 = posizione1;
    }

    public Posizione getPuntoMedio() {
        double xMedio = (posizione0.getX() + posizione1.getX()) / 2;
        double yMedio = (posizione0.getY() + posizione1.getY()) / 2;
        return new Posizione(xMedio, yMedio);
    }
}
