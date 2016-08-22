package it.uniroma3.stud.cidici.wifilocator.model;

import android.net.wifi.ScanResult;

import java.util.HashMap;
import java.util.Map;

public abstract class Planimetry {

    private final Map<String, Ap> bssidApMap = new HashMap<>(10);

    public Ap getAp(String bssid) {
        return bssidApMap.get(bssid);
    }

    public boolean isRomaTreAp(ScanResult scanResult) {
        return bssidApMap.containsKey(scanResult.BSSID);
    }

    public Map<String, Ap> getBssidApMap() {
        return bssidApMap;
    }

    protected void addAp(Ap ap) {
        for (String bssid : ap.getBssids()) {
            bssidApMap.put(bssid, ap);
        }

    }
}
