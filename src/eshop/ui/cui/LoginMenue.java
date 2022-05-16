package eshop.ui.cui;

import eshop.domain.Eshop;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
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
        System.out.println("(1) = Kundenanmeldung");
        System.out.println("(2) = Mitarbeiteranmeldung");
        System.out.println("(3) = Registrieren");
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
            }catch (IOException | EingabeNichtLeerException e){
                // TODO Auto-generated catch block
                System.out.println("\n" + e.getMessage());
                //e.printStackTrace();
            }

        }while(input != 0);
    }



    public void verarbeiteLoginEingabe(int line) throws IOException, EingabeNichtLeerException {
            int nutzernummer, hausnummer, plz;
            String name, passwort, strasse;

            switch(line){
                case 1:
                    Kunde k;
                    //Kunden Login
                    System.out.print("Geben Sie Ihre Kundennummer ein --> ");
                    nutzernummer = eingabeAusgabe.einlesenInteger();
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    try {
                        k = shop.kundenAnmelden(nutzernummer, passwort);

                        if(k.getNummer() == nutzernummer && k.getPasswort().equals(passwort)){
                            Warenkorb w = new Warenkorb(nutzernummer);

                            System.out.print("\nWillkommen im freeShop, "+ k.getName() + " schoen Sie zu sehen!");

                            kundenMenue.run();
                        }else{
                            System.out.println("Ungueltige Eingabe!");
                        }
                    }catch (NullPointerException e){
                        System.out.println("Ungueltige Eingabe!");
                    }





                    break;
                case 2:
                    Mitarbeiter m = null;
                    //Mitarbeiter Login
                    System.out.print("Geben Sie Ihre Mitarbeiternummer ein --> ");
                    nutzernummer = eingabeAusgabe.einlesenInteger();
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();
                    try {
                        m = shop.mitarbeiterAnmelden(nutzernummer, passwort);

                        if(m.getNummer() == nutzernummer && m.getPasswort().equals(passwort)){

                            System.out.print("\nWillkommen, "+ m.getName() + " arbeite du *********!");

                            mitarbeiterMenue.run();
                        }else{
                            System.out.println("Ungueltige Eingabe!");
                        }
                    }catch (NullPointerException e){
                        System.out.println("Ungueltige Eingabe!");
                    }


                    break;
                case 3:
                        //Registrierung (nur für Kunden)



                        System.out.println("Registrieren");
                        System.out.print("Geben Sie Ihren Namen ein -->");
                        name = eingabeAusgabe.einlesenString();

                        System.out.print("Geben Sie Ihr Passwort ein -->");
                        passwort = eingabeAusgabe.einlesenString();
                        System.out.print("Geben Sie Ihre Adresse ein: ");

                        System.out.print("\n\tGeben Sie Ihre Strasse ein -->");
                        strasse = eingabeAusgabe.einlesenString();

                        // TODO hier bin ich stehen geblieben(Niko) Exception für leere eingabe einbauen
                        /*if(name.isEmpty() || passwort.isEmpty() || strasse.isEmpty()){
                            throw new EingabeNichtLeerException(strasse, "HI");
                        }*/

                        System.out.print("\tGeben Sie Ihre Hausnummer ein -->");
                        hausnummer = eingabeAusgabe.einlesenInteger();
                        System.out.print("\tGeben Sie Ihre Postleitzahl ein -->");
                        plz = eingabeAusgabe.einlesenInteger();


                        System.out.print("\nIhre Kundennummer fuer die Anmeldung lautet --> " + shop.registriereKunden(name, passwort, strasse, hausnummer, plz) + "\n");

                    break;
            }




    }

}
