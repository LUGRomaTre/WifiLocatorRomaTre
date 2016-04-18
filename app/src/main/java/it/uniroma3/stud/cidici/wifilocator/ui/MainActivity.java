package it.uniroma3.stud.cidici.wifilocator.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import it.uniroma3.stud.cidici.wifilocator.R;
import it.uniroma3.stud.cidici.wifilocator.model.LocalizzatoreTriangolo;
import it.uniroma3.stud.cidici.wifilocator.model.Mappa;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;
import it.uniroma3.stud.cidici.wifilocator.model.PosizioneListener;

public class MainActivity extends AppCompatActivity implements PosizioneListener {

    private TextView posizioneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mappa mappa = new Mappa();
        LocalizzatoreTriangolo localizzatore = new LocalizzatoreTriangolo(this, mappa, -99999, this);

        setContentView(R.layout.activity_main);

        posizioneText = (TextView) findViewById(R.id.posizione);
        localizzatore.start();
    }

    @Override
    public void onPositionUpdate(Posizione posizione) {
        posizioneText.setText(posizione.toString());
    }

    @Override
    public void onPositionError(String message) {
        posizioneText.setText(message);
    }
}
