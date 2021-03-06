package eshop.domain.exceptions;

import eshop.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {
    private Artikel artikel;

    public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
        super("Artikel mit der Bezeichnung " + artikel.getBezeichnung()
                + " existiert bereits" + zusatzMsg);
        this.artikel = artikel;
    }

    public Artikel getArtikel() {
        return artikel;
    }
}
