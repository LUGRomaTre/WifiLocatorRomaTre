package it.uniroma3.stud.cidici.wifilocator.model;

import android.net.wifi.ScanResult;

public class ComparaScanResultsPerDbm implements java.util.Comparator<ScanResult> {
    @Override
    public int compare(ScanResult scanResult, ScanResult t1) {
        return scanResult.level - t1.level;
    }
}
