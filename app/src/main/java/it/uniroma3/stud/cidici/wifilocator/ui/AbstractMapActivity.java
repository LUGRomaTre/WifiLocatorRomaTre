package it.uniroma3.stud.cidici.wifilocator.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import it.uniroma3.stud.cidici.wifilocator.model.Mappa;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.CalcolatorePosizioneWifiTriangolo;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.Localizzatore;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.LocalizzatoreWifi;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.PosizioneListener;

public abstract class AbstractMapActivity extends AppCompatActivity implements PosizioneListener {

    protected static final int SOGLIA = -90;
    private Mappa mappa;

    public Mappa getMappa() {
        return mappa;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mappa = new Mappa();
        Localizzatore localizzatore = new LocalizzatoreWifi(this, mappa, SOGLIA, this, new CalcolatorePosizioneWifiTriangolo());

        loadContentView();
        checkPermissions();
        localizzatore.start();
    }

    protected abstract void loadContentView();

    private void checkPermissions() {
        boolean permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean b = permissionCheck && permissionCheck2;
        if (!b) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

}
