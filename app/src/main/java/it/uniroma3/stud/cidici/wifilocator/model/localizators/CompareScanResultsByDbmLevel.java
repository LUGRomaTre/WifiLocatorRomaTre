package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import android.net.wifi.ScanResult;

public class
CompareScanResultsByDbmLevel implements java.util.Comparator<ScanResult> {
    @Override
    public int compare(ScanResult scanResult, ScanResult t1) {
        return scanResult.level - t1.level;
    }
}
