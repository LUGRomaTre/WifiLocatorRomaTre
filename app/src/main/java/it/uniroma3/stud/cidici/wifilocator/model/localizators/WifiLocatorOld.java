package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Planimetry;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

import java.util.*;

/**
 * A Locator implementation that uses Wifi APs to locate the user.
 */
@SuppressWarnings("Duplicates")
public class WifiLocatorOld extends Locator {

    private static final int AP_GAIN = 14;
    private final Context context;
    private final SmartWifiCalculator smartWifiCalculator;
    private WifiManager wifiManager;
    private double gnagna = 5;

    /**
     * A Locator implementation that uses Wifi APs to locate the user.
     *
     * @param context    Android Context Object
     * @param planimetry an UniversityMap instance
     * @param listener   an object who gets the Position updates
     */
    public WifiLocatorOld(Context context, Planimetry planimetry, PositionListener listener) {
        super(listener, planimetry);
        this.context = context;
        smartWifiCalculator = new SmartWifiCalculator();
    }

    @Override
    public void start() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    private Position calculatePosition(List<ScanResult> scanResults) throws PositionNotFoundException {
        Set<Ap> aps = convertScanResultListToApList(scanResults);
        return smartWifiCalculator.findPosition(4, aps);
    }

    private Set<Ap> convertScanResultListToApList(List<ScanResult> srs) {
        Planimetry planimetry = getPlanimetry();
        Map<Ap, List<ScanResult>> apsFound = new HashMap<>();

        // Populate apsFound map
        for (ScanResult sr : srs) {
            Ap ap = planimetry.getAp(sr.BSSID);
            if (ap == null) continue;
            List<ScanResult> scanResultList = apsFound.get(ap);
            if (scanResultList == null) {
                scanResultList = new LinkedList<>();
                apsFound.put(ap, scanResultList);
            }
            scanResultList.add(sr);
        }

        // Calculate all levels
        for (Ap ap : apsFound.keySet()) {
            List<ScanResult> scanResultList = apsFound.get(ap);
            int levelMax = Integer.MIN_VALUE;
            for (ScanResult scanResult : scanResultList) {
                if (scanResult.level > levelMax) levelMax = scanResult.level;
            }
            ap.setLevel(levelMax);
        }

        return apsFound.keySet();
    }


    private void filtraListaSoloRomaTre(List<ScanResult> scanResults) {
        for (Iterator<ScanResult> iterator = scanResults.iterator(); iterator.hasNext(); ) {
            ScanResult scanResult = iterator.next();
            if (!getPlanimetry().isRomaTreAp(scanResult)) iterator.remove();
        }
    }

    private List<ScanResult> orderListScanByDbm(List<ScanResult> inputScanResults) {
        List<ScanResult> outputScanResults = new ArrayList<>(3);
        Collections.sort(inputScanResults, new CompareScanResultsByDbmLevel());
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