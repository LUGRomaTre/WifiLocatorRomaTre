package it.uniroma3.stud.cidici.wifilocator.model;

public interface PosizioneListener {

    void onPositionUpdate(Posizione posizione);

    void onPositionError(String message);
}
