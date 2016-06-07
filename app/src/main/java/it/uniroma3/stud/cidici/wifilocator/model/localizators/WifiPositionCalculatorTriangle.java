package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Position;
import it.uniroma3.stud.cidici.wifilocator.model.shapes.Segment;
import it.uniroma3.stud.cidici.wifilocator.model.shapes.Triangle;

import java.util.List;

public class WifiPositionCalculatorTriangle implements WifiPositionCalculator {

    @Override
    public Position calculatePosition(List<Ap> apList) throws PositionNotFoundException {
        if (apList.size() < 2) throw new PositionNotFoundException("Non riesco a localizzarti");

        Ap ap0 = apList.get(0);
        Ap ap1 = apList.get(1);

        if (apList.size() == 2) {
            Segment segment = new Segment(ap0.getPosition(), ap1.getPosition());
            return segment.getPuntoMedio();
        } else {
            Ap ap2 = apList.get(2);
            Triangle triangle = new Triangle(ap0.getPosition(), ap1.getPosition(), ap2.getPosition());
            return triangle.getBaricentro();
        }
    }
}
