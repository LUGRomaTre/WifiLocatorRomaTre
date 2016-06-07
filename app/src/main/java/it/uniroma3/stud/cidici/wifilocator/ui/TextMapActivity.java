package it.uniroma3.stud.cidici.wifilocator.ui;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.TextView;
import it.uniroma3.stud.cidici.wifilocator.R;
import it.uniroma3.stud.cidici.wifilocator.model.Position;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.CompareScanResultsByDbmLevel;

import java.util.Collections;
import java.util.List;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 13/05/16.
 */
public class TextMapActivity extends AbstractMapActivity {

    private TextView positionText;
    private WifiManager wifiManager;

    @Override
    protected void loadContentView() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        setContentView(R.layout.activity_map_text);
        positionText = (TextView) findViewById(R.id.position);
    }

    @Override
    public void onPositionUpdate(Position position) {
        printPosition(position.toString());
    }

    @Override
    public void onPositionError(String message) {
        printPosition(message);
    }

    private void printPosition(String message) {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Collections.sort(scanResults, new CompareScanResultsByDbmLevel());
        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.equals("Rm3Wi-Fi") && scanResult.level > DBM_THRESHOLD)
                message += "\n" + scanResult.BSSID + " " + scanResult.level;
        }
        positionText.setText(message);
    }

}
