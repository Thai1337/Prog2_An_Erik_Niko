package eshop.domain;

import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Warenkorb;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
public class Warenkorbverwaltung {

    // TODO für jeden Kunden ein eigen Warenkorb erstellen

    private Warenkorb warenkorb;
    public Warenkorbverwaltung(){
        // TODO Warenkorb zuweisung
    }

    public void warenkorbzuweisen(Artikel artikel, int anzahlArtikel, Kunde kunde){
        warenkorb = kunde.getWarkorb();
        warenkorb.setWarenkorbListe(artikel, anzahlArtikel);

        kunde.setMeinWarenkorb(warenkorb);
    }

    public Map<Artikel, Integer> getWarenkorb(Kunde kunde){
        warenkorb = kunde.getWarkorb();
        return warenkorb.getWarenkorbListe();
    }

}
