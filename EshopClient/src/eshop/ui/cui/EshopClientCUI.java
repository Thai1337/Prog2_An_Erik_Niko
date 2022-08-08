package eshop.ui.cui;

import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;

/**
 * Klasse für sehr einfache Benutzungsschnittstelle für den
 * E-Shop. Die Benutzungsschnittstelle basiert auf Ein-
 * und Ausgaben auf der Kommandozeile, daher der Name CUI
 * (Command line User Interface).
 */
public class EshopClientCUI {

    /**
     * Die main-Methode...
     */
    public static void main(String[] args) {

        LoginMenue loginMenue;
        try {
            loginMenue = new LoginMenue();
            loginMenue.run();
            // TODO Bestätigungsnachricht in Artikelverwaltung und andere
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
