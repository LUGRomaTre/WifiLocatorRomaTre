package it.uniroma3.stud.cidici.wifilocator.model;

import java.util.Map;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 31/07/16.
 */
public class HomeTestPlanimetry extends Planimetry {

    public HomeTestPlanimetry() {
        Ap cy = new Ap("88:07:4b:cd:84:6e", new Position(9, 17));
        Ap vodafone = new Ap("90:35:6e:5b:63:09", new Position(17, 34));
        Ap nexus5 = new Ap("02:1a:11:f5:0d:73", new Position(23, 1));
        //Ap nexus5x = new Ap("64:bc:0c:80:f0:52", new Position(25, 21));
        //Ap tablet = new Ap("",new Position(25, 21));
        Map<String, Ap> bssidApMap = getBssidApMap();
        bssidApMap.put(cy.getBssid(), cy);
        bssidApMap.put(vodafone.getBssid(), vodafone);
        bssidApMap.put(nexus5.getBssid(), nexus5);
//        bssidApMap.put(nexus5x.getBssid(), nexus5x);
//        bssidApMap.put(tablet.getBssid(), tablet);
    }
}
