package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Posizione;

import java.util.List;

public interface CalcolatorePosizioneWifi {

    Posizione calcolaPosizione(List<Ap> apList) throws PosizioneNonTrovataException;
}
