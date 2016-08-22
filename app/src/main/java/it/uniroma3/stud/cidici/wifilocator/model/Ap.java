package it.uniroma3.stud.cidici.wifilocator.model;

import java.util.LinkedList;
import java.util.List;

public class Ap {

    private final List<String> bssids;
    private final Position position;
    private int level;

    public Ap(List<String> bssids, Position position) {
        this.bssids = bssids;
        this.position = position;
    }

    public Ap(String bssid, Position position) {
        bssids = new LinkedList<>();
        bssids.add(bssid);
        this.position = position;
    }

    /**
     * @return
     * @deprecated
     */
    public String getBssid() {
        return bssids.get(0);
    }

    public List<String> getBssids() {
        return bssids;
    }

    public Position getPosition() {
        return position;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = (int) level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ap)) return false;

        Ap ap = (Ap) o;

        return bssids != null ? bssids.equals(ap.bssids) : ap.bssids == null;

    }

    @Override
    public int hashCode() {
        return bssids != null ? bssids.hashCode() : 0;
    }
}
