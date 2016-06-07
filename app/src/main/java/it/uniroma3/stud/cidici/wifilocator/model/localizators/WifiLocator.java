package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Position;
import it.uniroma3.stud.cidici.wifilocator.model.UniversityMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A Locator implementation that uses Wifi APs to locate the user.
 */
public class WifiLocator extends Locator {

    private final Context context;
    private final int dbmThreshold;
    private final WifiPositionCalculator wifiPositionCalculator;
    private WifiManager wifiManager;

    /**
     * A Locator implementation that uses Wifi APs to locate the user.
     *
     * @param context                Android Context Object
     * @param universityMap          an UniversityMap instance
     * @param dbmThreshold           APs with level below this value will not be considered. Example value: -87;
     * @param listener               an object who gets the Position updates
     * @param wifiPositionCalculator an object that calculate the position from a list of AP
     */
    public WifiLocator(Context context, UniversityMap universityMap, int dbmThreshold, PositionListener listener, WifiPositionCalculator wifiPositionCalculator) {
        super(listener, universityMap);
        this.context = context;
        this.dbmThreshold = dbmThreshold;
        this.wifiPositionCalculator = wifiPositionCalculator;
    }

    @Override
    public void start() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    private Position calculatePosition(List<ScanResult> scanResults) throws PositionNotFoundException {
        filtraListaSoloRomaTre(scanResults);
        scanResults = filtraListaScanResultPerDbmMaggioriDiSoglia(scanResults);

        if (scanResults.size() < 2) throw new PositionNotFoundException("Non riesco a localizzarti");

        List<Ap> apList = new ArrayList<>(scanResults.size());
        for (ScanResult scanResult : scanResults) {
            apList.add(getUniversityMap().getAp(scanResult.BSSID));
        }

        return wifiPositionCalculator.calculatePosition(apList);
    }

    private void filtraListaSoloRomaTre(List<ScanResult> scanResults) {
        for (Iterator<ScanResult> iterator = scanResults.iterator(); iterator.hasNext(); ) {
            ScanResult scanResult = iterator.next();
            if (!getUniversityMap().isRomaTreAp(scanResult)) iterator.remove();
        }
    }

    private List<ScanResult> filtraListaScanResultPerDbmMaggioriDiSoglia(List<ScanResult> inputScanResults) {
        List<ScanResult> outputScanResults = new ArrayList<>(3);
        Collections.sort(inputScanResults, new CompareScanResultsByDbmLevel());
        for (ScanResult scanResult : inputScanResults) {
            if (outputScanResults.size() >= 3) break;
            if (scanResult.level > dbmThreshold) outputScanResults.add(scanResult);
        }
        return outputScanResults;
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                updatePosition(calculatePosition(wifiManager.getScanResults()));
            } catch (PositionNotFoundException e) {
                positionError(e.getMessage());
            }
            wifiManager.startScan();
        }
    }
}