package eshop.domain;

import eshop.valueobjects.Warenkorb;

import java.util.HashMap;
import java.util.Map;

public class Warenkorbverwaltung {
    // TODO für jeden Kunden ein eigen Warenkorb erstellen
    private Map warenkoerbe;

    public Warenkorbverwaltung(){
        // TODO Warenkorb zuweisung
        warenkoerbe = new HashMap();
    }

    public void warenkorbzuweisen(){
        //Warenkorb w = new Warenkorb(3);

    }
}
