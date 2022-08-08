package eshop.domain.exceptions;

import eshop.valueobjects.Massengutartikel;

public class MassengutartikelBestandsException extends Exception {
    public MassengutartikelBestandsException(Massengutartikel massengutartikel){
        super("\n Massengutartikel: "+ massengutartikel.getBezeichnung() + " mit der Artikelnummer: " + massengutartikel.getNummer() +
                "\n Der von ihnen angegebe Bestand ist kein Vielfaches der Packungsgrosse: " + massengutartikel.getPackungsgrosse());
    }

    public MassengutartikelBestandsException(Massengutartikel massengutartikel, int bestellmenge){
        super("\n Massengutartikel: "+ massengutartikel.getBezeichnung() + " mit der Artikelnummer: " + massengutartikel.getNummer() +
                "\n Der von ihnen angegebe Bestand: [" + bestellmenge + "] ist kein Vielfaches der Packungsgrosse: [" + massengutartikel.getPackungsgrosse() + "]");
    }
}
