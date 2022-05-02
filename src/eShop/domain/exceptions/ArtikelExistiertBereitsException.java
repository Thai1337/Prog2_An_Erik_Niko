package eShop.domain.exceptions;

import eShop.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {
    private Artikel artikel;

    public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
        super("Artikel mit der Bezeichnung " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer()
                + " existiert bereits" + zusatzMsg);
        this.artikel = artikel;
    }

    public Artikel getBuch() {
        return artikel;
    }
}
