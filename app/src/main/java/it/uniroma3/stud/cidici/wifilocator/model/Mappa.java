package it.uniroma3.stud.cidici.wifilocator.model;

import android.net.wifi.ScanResult;

import java.util.HashMap;
import java.util.Map;

public class Mappa {

    private final Map<String, Ap> bssidApMap;

    public Mappa() {
        Ap ap0 = new Ap("7c:0e:ce:4c:09:a0", new Posizione(4, 2));
        Ap ap1 = new Ap("58:f3:9c:b5:19:c0", new Posizione(0, 19));
        Ap ap2 = new Ap("7c:0e:ce:2a:43:b0", new Posizione(0, 14));
        bssidApMap = new HashMap<>(10);
        bssidApMap.put(ap0.getBssid(), ap0);
        bssidApMap.put(ap1.getBssid(), ap1);
        bssidApMap.put(ap2.getBssid(), ap2);
    }

    public Ap getAp(String bssid) {
        return bssidApMap.get(bssid);
    }

    public boolean isRomaTreAp(ScanResult scanResult) {
        return bssidApMap.containsKey(scanResult.BSSID);
    }
}
