package eshop.domain.exceptions;

public class ArtikelNichtVorhandenException extends Exception{

    public ArtikelNichtVorhandenException(){

            super("\n Der von Ihnen angegebene Artikel befindet konnte nicht gedunden werden.\n Bitte geben Sie eine gueltige Artikelnummer ein!");

    }
}
