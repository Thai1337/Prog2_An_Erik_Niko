package eShop.domain;

import eShop.valueobjects.Artikel;

import java.util.Vector;

public class Artikelverwaltung {
    private Vector ArtikelBestand = new Vector();

    public Artikelverwaltung(){
        Artikel a1 = new Artikel(10, "Holz", 100);
        Artikel a2 = new Artikel(1, "Metall", 50);
        ArtikelBestand.add(a1);
        ArtikelBestand.add(a2);


    }

    public Vector getArtikelBestand() {
        return new Vector(ArtikelBestand);

    }

}

