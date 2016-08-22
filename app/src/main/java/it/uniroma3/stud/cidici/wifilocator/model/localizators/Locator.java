package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Planimetry;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

/**
 * A component that with different methods and technologies finds the user position.
 */
public abstract class Locator {
    private PositionListener positionListener;
    private Planimetry planimetry;

    public Locator(PositionListener positionListener, Planimetry planimetry) {
        this.positionListener = positionListener;
        this.planimetry = planimetry;
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

    public Planimetry getPlanimetry() {
        return planimetry;
    }

    /**
     * Tell the Locator to start find user position.
     */
    public abstract void start();

}
