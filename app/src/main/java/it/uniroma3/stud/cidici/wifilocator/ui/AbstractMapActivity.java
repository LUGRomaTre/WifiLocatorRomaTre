package it.uniroma3.stud.cidici.wifilocator.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import it.uniroma3.stud.cidici.wifilocator.model.UniversityMap;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.Locator;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.PositionListener;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.WifiLocator;
import it.uniroma3.stud.cidici.wifilocator.model.localizators.WifiPositionCalculatorTriangle;

/**
 * You can extend this Activity to simple create a UI for show user location
 */
public abstract class AbstractMapActivity extends AppCompatActivity implements PositionListener {

    protected static final int DBM_THRESHOLD = -95;
    private UniversityMap universityMap;

    public UniversityMap getUniversityMap() {
        return universityMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        universityMap = new UniversityMap();
        Locator locator = new WifiLocator(this, universityMap, DBM_THRESHOLD, this, new WifiPositionCalculatorTriangle());

        loadContentView();
        checkPermissions();
        locator.start();
    }

    /**
     * This method should initialize the content View
     */
    protected abstract void loadContentView();

    /**
     * Android 6.0 permissions check
     */
    private void checkPermissions() {
        boolean permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean b = permissionCheck && permissionCheck2;
        if (!b) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

}
