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
public class WifiLocator extends Locator {

    private static final int AP_GAIN = 14;
    private final Context context;
    private WifiManager wifiManager;
    private double gnagna = 5;

    /**
     * A Locator implementation that uses Wifi APs to locate the user.
     *
     * @param context                Android Context Object
     * @param planimetry             an UniversityMap instance
     * @param listener               an object who gets the Position updates
     */
    public WifiLocator(Context context, Planimetry planimetry, PositionListener listener) {
        super(listener, planimetry);
        this.context = context;
    }

    @Override
    public void start() {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

//    private Position calculatePosition(List<ScanResult> scanResults) throws PositionNotFoundException {
//        filtraListaSoloRomaTre(scanResults);
//        scanResults = orderListScanByDbm(scanResults);
//
//        if (scanResults.size() < 2) throw new PositionNotFoundException("Non riesco a localizzarti");
//
//        List<Ap> apList = new ArrayList<>(scanResults.size());
//        for (ScanResult scanResult : scanResults) {
//            apList.add(getPlanimetry().getAp(scanResult.BSSID));
//        }
//
//        return wifiPositionCalculator.calculatePosition(apList);
//    }

    private Position calculatePosition(List<ScanResult> scanResults) throws PositionNotFoundException {
        double xSum = 0, totWeight = 0, ySum = 0;
        Set<Ap> aps = convertScanResultListToApList(scanResults);
        for (Ap ap : aps) {
            double weight = Math.pow(1.0 / (-ap.getLevel()), gnagna);
            Position position = ap.getPosition();
            xSum += weight * position.getX();
            ySum += weight * position.getY();
            totWeight += weight;
        }
        return new Position(xSum / totWeight, ySum / totWeight);
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

        // Calculate all distances
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