package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

import java.util.List;

public interface WifiPositionCalculator {

    /**
     * Calculate the user Position from a list of Ap
     *
     * @param apList the List of Ap used to calculate the user position
     * @return the user position
     * @throws PositionNotFoundException it was impossible to find the user position, maybe for insufficient APs.
     */
    Position calculatePosition(List<Ap> apList) throws PositionNotFoundException;
}
