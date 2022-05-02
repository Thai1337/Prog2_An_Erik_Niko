package eshop.domain.exceptions;

import eshop.valueobjects.Artikel;

public class ArtikelbestandUnterNullException extends Exception{

    private Artikel artikel;

    public ArtikelbestandUnterNullException(Artikel artikel, String zusatzMsg) {
        super("Artikel mit der Bezeichnung " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer()
                + " darf nicht unter 0 Fallen" + zusatzMsg);
        this.artikel = artikel;
    }

    public Artikel getArtikel() {
        return artikel;
    }
}
