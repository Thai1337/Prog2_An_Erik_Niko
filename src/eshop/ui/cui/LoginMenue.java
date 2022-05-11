package eshop.ui.cui;

import eshop.domain.Eshop;

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
        System.out.println("\nWillkommen in unserem eShop!");
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
            }catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }while(input != 0);
    }



    public void verarbeiteLoginEingabe(int line) throws IOException {
            int nutzernummer;
            String name, adresse, passwort;

            switch(line){
                case 1:
                    //Kunden Login
                    System.out.print("Geben Sie Ihre Kundennummer ein --> ");
                    nutzernummer = Integer.parseInt(eingabeAusgabe.einlesenString());
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    if(shop.kundenAnmelden(nutzernummer, passwort)){
                        kundenMenue.run();
                    }else{
                        System.out.println("Ungueltige Eingabe!");
                    }
                    break;
                case 2:
                    //Mitarbeiter Login
                    System.out.print("Geben Sie Ihre Mitarbeiternummer ein --> ");
                    nutzernummer = Integer.parseInt(eingabeAusgabe.einlesenString());
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesenString();

                    if(shop.mitarbeiterAnmelden(nutzernummer, passwort)){
                        mitarbeiterMenue.run();
                    }else{
                        System.out.println("Ungueltige Eingabe!");
                    }

                    break;
                case 3:
                        //Registrierung (nur für Kunden)
                        System.out.println("Registrieren");
                        System.out.print("Geben Sie Ihren Namen ein -->");
                        name = eingabeAusgabe.einlesenString();
                        System.out.print("Geben Sie Ihre Adresse ein -->");
                        adresse = eingabeAusgabe.einlesenString();
                        System.out.print("Geben Sie Ihr Passwort ein -->");
                        passwort = eingabeAusgabe.einlesenString();

                        System.out.print("\nIhre Kundennummer fuer die Anmeldung lautet --> " + shop.registriereKunden(name, adresse, passwort) + "\n");

                    break;
            }




    }

}
