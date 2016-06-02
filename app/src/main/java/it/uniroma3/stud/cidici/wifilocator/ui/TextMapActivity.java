package it.uniroma3.stud.cidici.wifilocator.ui;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.TextView;
import it.uniroma3.stud.cidici.wifilocator.R;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.ComparaScanResultsPerDbm;

import java.util.Collections;
import java.util.List;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 13/05/16.
 */
public class TextMapActivity extends AbstractMapActivity {

    private TextView posizioneText;
    private WifiManager wifiManager;

    @Override
    protected void loadContentView() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        setContentView(R.layout.activity_map_text);
        posizioneText = (TextView) findViewById(R.id.posizione);
    }

    @Override
    public void onPositionUpdate(Posizione posizione) {
        stampaPosizione(posizione.toString());
    }

    @Override
    public void onPositionError(String message) {
        stampaPosizione(message);
    }

    private void stampaPosizione(String messaggio) {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Collections.sort(scanResults, new ComparaScanResultsPerDbm());
        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.equals("Rm3Wi-Fi") && scanResult.level > SOGLIA)
                messaggio += "\n" + scanResult.BSSID + " " + scanResult.level;
        }
        posizioneText.setText(messaggio);
    }

}
