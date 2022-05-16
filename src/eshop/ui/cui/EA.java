package eshop.ui.cui;

import eshop.domain.exceptions.EingabeNichtLeerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class EA {
    // TODO try catch (Exceptions) einfügen um diese bei den eingabe switch Cases einzusparen
    private BufferedReader in;

    public EA(){
        in = new BufferedReader(new InputStreamReader(System.in));
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Einlesen von Benutzereingaben.
     */
    private String einlesen() throws IOException {
        //einlesen der Nutzereingaben
        return in.readLine();
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Einlesen von Benutzereingaben des Type String.
     */
    public String einlesenString(){
        String eingabe;
        try {
            eingabe = einlesen();
            return eingabe;
        }catch (Exception e) {
            //System.out.println("Fehler bei der Eingabe!!!!!");
            return "";
        }
    }

    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Einlesen von Benutzereingaben des Type Int.
     */
    public int einlesenInteger(){
        String eingabe;
        try {
            eingabe = einlesen();
            return Integer.parseInt(eingabe);
        }catch (Exception e ) {
            //System.out.println("Fehler bei der Eingabe!!!!!");
            return -1;
        }
    }
    /* (non-Javadoc)
     *
     * Interne (private) Methode zum Ausgeben von Listen.
     *
     */
    public void gibListeAus(List liste) {
        if (liste.isEmpty()) {
            System.out.println("Liste ist leer.");
        } else {
            // Durchlaufen des Vectors mittels for each-Schleife
            // (alternativ: Iterator)
            for (Object artikel: liste) {
                System.out.println(artikel);
            }
        }
    }

}