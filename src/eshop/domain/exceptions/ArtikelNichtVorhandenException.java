package eshop.domain.exceptions;

import eshop.valueobjects.Artikel;

public class ArtikelNichtVorhandenException extends Exception {

    public ArtikelNichtVorhandenException() {

        super("\n Der von Ihnen angegebene Artikel konnte nicht gefunden werden.\n Bitte geben Sie eine gueltige Artikelnummer ein!");

    }

    public ArtikelNichtVorhandenException(Artikel artikel, String msg) {

        super("\n Der Artikel " + artikel.getBezeichnung() + " konnte nicht gefunden werden.\n" + msg);

    }
}
