package it.uniroma3.stud.cidici.wifilocator.model;

import android.net.wifi.ScanResult;

import java.util.HashMap;
import java.util.Map;

public class UniversityMap {

    private final Map<String, Ap> bssidApMap;

    public UniversityMap() {
        Ap ds1 = new Ap("7c:0e:ce:2a:43:b0", new Position(12.1, 7.3));
        Ap ds2A = new Ap("58:f3:9c:80:88:f0", new Position(9.2, 6.8));
        Ap ds2B = new Ap("58:f3:9c:b5:19:c0", new Position(12, 6.8));
        Ap ds3b = new Ap("7c:0e:ce:15:94:40", new Position(6.6, 7.8));
        Ap n1 = new Ap("7c:0e:ce:4c:09:a0", new Position(11.3, 8.8));
        Ap n3 = new Ap("7c:0e:ce:15:9a:30", new Position(6.7, 5.8));
        Ap n5 = new Ap("7c:0e:ce:15:9a:00", new Position(3.5, 5.6));
        Ap n6 = new Ap("7c:0e:ce:15:94:00", new Position(4.5, 7.5));
        Ap n8 = new Ap("64:d8:14:2a:38:60", new Position(2.6, 8));
        Ap n10 = new Ap("58:f3:9c:80:78:50", new Position(0.7, 5));
        Ap corr10 = new Ap("7c:0e:ce:2a:43:d0", new Position(1.2, 7));
        Ap corrEntr = new Ap("64:d8:14:b3:48:60", new Position(1.8, 1.8));
        Ap b1 = new Ap("58:f3:9c:b5:2f:d0", new Position(5.7, 3.8));
        Ap mensa = new Ap("58:f3:9c:80:a9:a0", new Position(0, 14));
        bssidApMap = new HashMap<>(10);
        bssidApMap.put(ds1.getBssid(), ds1);
        bssidApMap.put(ds2A.getBssid(), ds2A);
        bssidApMap.put(ds2B.getBssid(), ds2B);
        bssidApMap.put(ds3b.getBssid(), ds3b);
        bssidApMap.put(n1.getBssid(), n1);
        bssidApMap.put(n3.getBssid(), n3);
        bssidApMap.put(n5.getBssid(), n5);
        bssidApMap.put(n6.getBssid(), n6);
        bssidApMap.put(n8.getBssid(), n8);
        bssidApMap.put(n10.getBssid(), n10);
        bssidApMap.put(corr10.getBssid(), corr10);
        bssidApMap.put(corrEntr.getBssid(), corrEntr);
        bssidApMap.put(b1.getBssid(), b1);
    }

    public Ap getAp(String bssid) {
        return bssidApMap.get(bssid);
    }

    public boolean isRomaTreAp(ScanResult scanResult) {
        return bssidApMap.containsKey(scanResult.BSSID);
    }

    public Map<String, Ap> getBssidApMap() {
        return bssidApMap;
    }
}
