package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import android.content.Context;
import it.uniroma3.stud.cidici.wifilocator.model.Planimetry;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 03/08/16.
 */
public class CompositeLocator extends Locator implements PositionListener {

    private final WifiLocator wifiLocator;
    private final PositionListener positionListener;
    private final Queue<Position> positions = new ArrayDeque<>(10);

    public CompositeLocator(PositionListener positionListener, Planimetry planimetry, Context context) {
        super(positionListener, planimetry);
        this.positionListener = positionListener;
        wifiLocator = new WifiLocator(context, planimetry, this);
    }

    @Override
    public void start() {
        wifiLocator.start();
    }


    @Override
    public void onPositionUpdate(Position position) {
        positions.add(position);
        double sumWeight = 0;
        double sumX = 0;
        double sumY = 0;
        long currentTimestamp = position.getTimestamp();
        for (Position p : positions) {
            double weight = Math.exp(-(currentTimestamp - p.getTimestamp()) / 2000.0);
            sumX += p.getX() * weight;
            sumY += p.getY() * weight;
            sumWeight += weight;
        }
        positionListener.onPositionUpdate(new Position(sumX / sumWeight, sumY / sumWeight));
    }

    @Override
    public void onPositionError(String message) {
        positionListener.onPositionError(message);
    }
}