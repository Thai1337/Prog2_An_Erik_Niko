package eshop.domain.exceptions;

public class ArtikelNichtVorhandenException extends Exception{

    public ArtikelNichtVorhandenException(String msg){

            super("\n Der von Ihnen angegebene Artikel befindet sich nicht in " + msg + "\n Bitte geben Sie eine gueltige Artikelnummer ein!");

    }
}
