package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;
import eshop.valueobjects.Warenkorb;

import java.io.IOException;

public class LoginMenue {
    private KundenMenue kundenMenue;
    private MitarbeiterMenue mitarbeiterMenue;

    private Eshop shop;
    private EA eingabeAusgabe;
    public LoginMenue(){
        shop = new Eshop();
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
    public void run(){
        int input = -1;
        do{
            gibLoginMenueAus();
            try{
                input = eingabeAusgabe.einlesenInteger();
                verarbeiteLoginEingabe(input);
            }catch (IOException | EingabeNichtLeerException | AnmeldungFehlgeschlagenException e){
                // TODO Auto-generated catch block
                System.out.println("\n" + e.getMessage());
                //e.printStackTrace();
            }

        }while(input != 0);
    }



    public void verarbeiteLoginEingabe(int line) throws IOException, EingabeNichtLeerException, AnmeldungFehlgeschlagenException {
            int nutzernummer, hausnummer, plz;
            String name, passwort, strasse;

            switch(line){
                /*case 1:
                    Kunde k;
                    //Kunden Login
                    System.out.print("Geben Sie Ihre Kundennummer ein --> ");
                    nutzernummer = eingabeAusgabe.einlesenInteger();
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    k = shop.kundenAnmelden(nutzernummer, passwort);
                    kundenMenue.setKunde(k);
                    System.out.print("\nWillkommen im freeShop, "+ k.getName() + " schoen Sie zu sehen!");
                    kundenMenue.run();


                    break;
                case 2:
                    Mitarbeiter m = null;
                    //Mitarbeiter Login
                    System.out.print("Geben Sie Ihre Mitarbeiternummer ein --> ");
                    nutzernummer = eingabeAusgabe.einlesenInteger();
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    m = shop.mitarbeiterAnmelden(nutzernummer, passwort);
                    System.out.print("\nWillkommen, "+ m.getName() + " arbeite du *********!");
                    mitarbeiterMenue.run();

                    break;*/
                case 1:

                    System.out.print("Geben Sie Ihre Nummer ein --> ");
                    nutzernummer = eingabeAusgabe.einlesenInteger();
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    Mitarbeiter mitarbeiter = shop.mitarbeiterAnmelden(nutzernummer, passwort); // Die reihenfolge ist wichtig, weil nur kundenAnmelden eine exception werfen kann, mitarbeiterAnmelden muss null returnen können sonst bricht die Anmeldung ab
                    if(mitarbeiter != null){
                        System.out.print("\nWillkommen, "+ mitarbeiter.getName() + " arbeite du *********!");
                        mitarbeiterMenue.run();
                    }else{ // das else wird benötigt, damit beim ausloggen keine exception geworfen wird
                        Kunde kunde = shop.kundenAnmelden(nutzernummer, passwort);
                        kundenMenue.setKunde(kunde);
                        System.out.print("\nWillkommen im freeShop, "+ kunde.getName() + " schoen Sie zu sehen!");
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


                    System.out.print("\nIhre Kundennummer fuer die Anmeldung lautet --> " + shop.registriereKunden(name, passwort, strasse, hausnummer, plz) + "\n");

                    break;
            }




    }

}
