package it.uniroma3.stud.cidici.wifilocator.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import it.uniroma3.stud.cidici.wifilocator.R;
import it.uniroma3.stud.cidici.wifilocator.model.Mappa;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.*;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PosizioneListener {

    private static final int SOGLIA = -90;
    private TextView posizioneText;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mappa mappa = new Mappa();
        Localizzatore localizzatore = new LocalizzatoreWifi(this, mappa, SOGLIA, this, new CalcolatorePosizioneWifiTriangolo());
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        setContentView(R.layout.activity_main);

        posizioneText = (TextView) findViewById(R.id.posizione);
        checkPermissions();
        localizzatore.start();
    }

    private void checkPermissions() {
        boolean permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean b = permissionCheck && permissionCheck2;
        if (!b) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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
