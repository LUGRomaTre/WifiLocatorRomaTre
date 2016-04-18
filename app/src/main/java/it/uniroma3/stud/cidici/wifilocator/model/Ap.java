package it.uniroma3.stud.cidici.wifilocator.model;

public class Ap {

    private final String bssid;
    private final Posizione posizione;

    public Ap(String bssid, Posizione posizione) {
        this.bssid = bssid;
        this.posizione = posizione;
    }

    public String getBssid() {
        return bssid;
    }

    public Posizione getPosizione() {
        return posizione;
    }
}
