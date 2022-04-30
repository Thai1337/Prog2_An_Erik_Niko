package eShop.domain;

import java.io.IOException;
import java.util.Vector;

public class Eshop {

    private Artikelverwaltung artikelVW;

    public Eshop() {
        artikelVW = new Artikelverwaltung();
    }
  public Vector gibAlleArtikel(){
      // einfach delegieren an meineBuecher
      return artikelVW.getArtikelBestand();
  }
}
