package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Mappa;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LocalizzatoreWifi extends Localizzatore {

    private final Context context;
    private final int soglia;
    private final CalcolatorePosizioneWifi calcolatorePosizioneWifi;
    private WifiManager wifiManager;

    public LocalizzatoreWifi(Context context, Mappa mappa, int soglia, PosizioneListener listener, CalcolatorePosizioneWifi calcolatorePosizioneWifi) {
        super(listener, mappa);
        this.context = context;
        this.soglia = soglia;
        this.calcolatorePosizioneWifi = calcolatorePosizioneWifi;
    }

    @Override
    public void start() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    private Posizione calcolaPosizione(List<ScanResult> scanResults) throws PosizioneNonTrovataException {
        filtraListaSoloRomaTre(scanResults);
        scanResults = filtraListaScanResultPerDbmMaggioriDiSoglia(scanResults);

        if (scanResults.size() < 2) throw new PosizioneNonTrovataException("Non riesco a localizzarti");

        List<Ap> apList = new ArrayList<>(scanResults.size());
        for (ScanResult scanResult : scanResults) {
            apList.add(getMappa().getAp(scanResult.BSSID));
        }

        return calcolatorePosizioneWifi.calcolaPosizione(apList);
    }

    private void filtraListaSoloRomaTre(List<ScanResult> scanResults) {
        for (Iterator<ScanResult> iterator = scanResults.iterator(); iterator.hasNext(); ) {
            ScanResult scanResult = iterator.next();
            if (!getMappa().isRomaTreAp(scanResult)) iterator.remove();
        }
    }

    private List<ScanResult> filtraListaScanResultPerDbmMaggioriDiSoglia(List<ScanResult> inputScanResults) {
        List<ScanResult> outputScanResults = new ArrayList<>(3);
        Collections.sort(inputScanResults, new ComparaScanResultsPerDbm());
        for (ScanResult scanResult : inputScanResults) {
            if (outputScanResults.size() >= 3) break;
            if (scanResult.level > soglia) outputScanResults.add(scanResult);
        }
        return outputScanResults;
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                aggiornaPosizione(calcolaPosizione(wifiManager.getScanResults()));
            } catch (PosizioneNonTrovataException e) {
                errorePosizione(e.getMessage());
            }
            wifiManager.startScan();
        }
    }
}