package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Position;
import it.uniroma3.stud.cidici.wifilocator.model.UniversityMap;

/**
 * A component that with different methods and technologies finds the user position.
 */
public abstract class Locator {
    private PositionListener positionListener;
    private UniversityMap universityMap;

    public Locator(PositionListener positionListener, UniversityMap universityMap) {
        this.positionListener = positionListener;
        this.universityMap = universityMap;
    }

    protected void updatePosition(Position position) {
        positionListener.onPositionUpdate(position);
    }

    protected void positionError(String message) {
        positionListener.onPositionError(message);
    }

    public PositionListener getPositionListener() {
        return positionListener;
    }

    public void setPositionListener(PositionListener positionListener) {
        this.positionListener = positionListener;
    }

    public UniversityMap getUniversityMap() {
        return universityMap;
    }

    /**
     * Tell the Locator to start find user position.
     */
    public abstract void start();

}
