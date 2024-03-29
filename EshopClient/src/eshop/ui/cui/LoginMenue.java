package eshop.ui.cui;

import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginMenue {
    private static int DEFAULT_PORT = 1099;
    private EA eingabeAusgabe;

    private KundenMenue kundenMenue;
    private MitarbeiterMenue mitarbeiterMenue;

    private EshopSerializable shop;

    public LoginMenue() throws IOException, ClassNotFoundException {
        String serviceName = "eShopService";
        String host = "localhost";
        int port = DEFAULT_PORT;
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            this.shop = (EshopSerializable) registry.lookup(serviceName); // Variante mit Serializable
        }

        catch (NotBoundException e) {
            // unter der URL ist kein RMI-Objekt registriert
            e.printStackTrace();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        kundenMenue = new KundenMenue(shop);
        mitarbeiterMenue = new MitarbeiterMenue(shop);
        eingabeAusgabe = new EA();

    }


    public void gibLoginMenueAus() {
        System.out.println("\nWillkommen in unserem freeShop!");
        System.out.println("(1) = Anmelden");
        System.out.println("(2) = Registrieren");
        System.out.println("(0) = Programm beenden\n");
        System.out.print("Eingabe --> ");
    }

    /**
     * Methode zur Ausführung der Hauptschleife:
     * - Login-Menü ausgeben
     * - Eingabe des Benutzers einlesen
     * - Eingabe verarbeiten und Ergebnis ausgeben
     * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
     */
    public void run() {
        int input = -1;
        do {
            gibLoginMenueAus();
            try {
                input = eingabeAusgabe.einlesenInteger();
                verarbeiteLoginEingabe(input);
            } catch (IOException | EingabeNichtLeerException | AnmeldungFehlgeschlagenException e) {
                // TODO Auto-generated catch block
                System.out.println("\n" + e.getMessage());
                //e.printStackTrace();
            }

        } while (input != 0);
    }

    /* (non-Javadoc)
     *
     * Interne (private) Methode zur Verarbeitung von Eingaben
     * und Ausgabe von Ergebnissen.
     */
    private void verarbeiteLoginEingabe(int line) throws IOException, EingabeNichtLeerException, AnmeldungFehlgeschlagenException {
        int nutzernummer, hausnummer, plz;
        String name, passwort, strasse, ort;

        switch (line) {
            case 1:

                System.out.print("Geben Sie Ihre Nummer ein --> ");
                nutzernummer = eingabeAusgabe.einlesenInteger();
                System.out.print("Geben Sie Ihr Passwort ein --> ");
                passwort = eingabeAusgabe.einlesenString();

                Mitarbeiter mitarbeiter = shop.mitarbeiterAnmelden(nutzernummer, passwort); // Die Reihenfolge ist wichtig, weil nur kundenAnmelden() eine exception werfen kann, mitarbeiterAnmelden muss null returnen können sonst bricht die Anmeldung ab
                if (mitarbeiter != null) {
                    mitarbeiterMenue.setMitarbeiter(mitarbeiter); // wird für das logging benötigt
                    System.out.print("\nWillkommen, " + mitarbeiter.getName() + " arbeiten Sie bitte!");
                    mitarbeiterMenue.run();
                } else { // das else wird benötigt, damit beim ausloggen keine exception geworfen wird
                    Kunde kunde = shop.kundenAnmelden(nutzernummer, passwort); // nur kundenAnmelden() wirft eine Anmeldung fehlgeschlagen exception
                    kundenMenue.setKunde(kunde);
                    System.out.print("\nWillkommen im freeShop, " + kunde.getName() + " schoen Sie zu sehen!");
                    kundenMenue.run();
                }


                break;
            case 2:
                //Registrierung (nur für Kunden)

                System.out.println("Registrieren");
                System.out.print("Geben Sie Ihren Namen ein -->");
                name = eingabeAusgabe.einlesenString();

                System.out.print("Geben Sie Ihr Passwort ein -->");
                passwort = eingabeAusgabe.einlesenString();
                System.out.print("Geben Sie Ihre Adresse ein: ");

                System.out.print("\n\tGeben Sie Ihre Strasse ein -->");
                strasse = eingabeAusgabe.einlesenString();

                System.out.print("\tGeben Sie Ihre Hausnummer ein -->");
                hausnummer = eingabeAusgabe.einlesenInteger();
                System.out.print("\tGeben Sie Ihre Postleitzahl ein -->");
                plz = eingabeAusgabe.einlesenInteger();
                System.out.print("\tGeben Sie Ihren Ort an ein -->");
                ort = eingabeAusgabe.einlesenString();


                System.out.print("\nIhre Kundennummer fuer die Anmeldung lautet --> " + shop.registriereKunden(name, passwort, strasse, hausnummer, plz, ort) + "\n");

                break;
        }
    }

}
