package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Position;

public interface PositionListener {

    /**
     * This method is called by a Localizzatore when the Posizione changes
     *
     * @param position
     */
    void onPositionUpdate(Position position);

    /**
     * This method is called by a Localizzatore when there is a problem getting the Posizione
     *
     * @param message
     */
    void onPositionError(String message);
}
