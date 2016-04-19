package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;

import java.util.List;

public class CalcolatorePosizioneWifiTriangolo implements CalcolatorePosizioneWifi {

    @Override
    public Posizione calcolaPosizione(List<Ap> apList) throws PosizioneNonTrovataException {
        if (apList.size() < 2) throw new PosizioneNonTrovataException("Non riesco a localizzarti");

        Ap ap0 = apList.get(0);
        Ap ap1 = apList.get(1);

        if (apList.size() == 2) {
            Segmento segmento = new Segmento(ap0.getPosizione(), ap1.getPosizione());
            return segmento.getPuntoMedio();
        } else {
            Ap ap2 = apList.get(2);
            Triangolo triangolo = new Triangolo(ap0.getPosizione(), ap1.getPosizione(), ap2.getPosizione());
            return triangolo.getBaricentro();
        }
    }
}
