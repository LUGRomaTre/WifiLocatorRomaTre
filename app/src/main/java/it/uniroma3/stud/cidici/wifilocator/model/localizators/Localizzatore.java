package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Mappa;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;

public abstract class Localizzatore {
    private PosizioneListener posizioneListener;
    private Mappa mappa;

    public Localizzatore(PosizioneListener posizioneListener, Mappa mappa) {
        this.posizioneListener = posizioneListener;
        this.mappa = mappa;
    }

    protected void aggiornaPosizione(Posizione posizione) {
        posizioneListener.onPositionUpdate(posizione);
    }

    protected void errorePosizione(String message) {
        posizioneListener.onPositionError(message);
    }

    public PosizioneListener getPosizioneListener() {
        return posizioneListener;
    }

    public void setPosizioneListener(PosizioneListener posizioneListener) {
        this.posizioneListener = posizioneListener;
    }

    public Mappa getMappa() {
        return mappa;
    }

    public abstract void start();

}
