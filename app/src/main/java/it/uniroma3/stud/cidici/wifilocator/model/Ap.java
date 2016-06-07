package it.uniroma3.stud.cidici.wifilocator.model;

public class Ap {

    private final String bssid;
    private final Position position;

    public Ap(String bssid, Position position) {
        this.bssid = bssid;
        this.position = position;
    }

    public String getBssid() {
        return bssid;
    }

    public Position getPosition() {
        return position;
    }
}
