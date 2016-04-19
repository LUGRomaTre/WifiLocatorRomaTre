package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Posizione;

public interface PosizioneListener {

    void onPositionUpdate(Posizione posizione);

    void onPositionError(String message);
}
