package eshop.ui.cui;

import java.io.IOException;

public class LoginMenue {
    private KundenMenue kundenMenue;
    private MitarbeiterMenue mitarbeiterMenue;
    private EA eingabeAusgabe;
    public LoginMenue(){
        kundenMenue = new KundenMenue();
        mitarbeiterMenue = new MitarbeiterMenue();
        eingabeAusgabe = new EA();
    }



    public void gibLoginMenueAus() {
        System.out.println("\nWillkommen in unserem eShop!");
        System.out.println("(a) = Kundenanmeldung");
        System.out.println("(b) = Mitarbeiteranmeldung");
        System.out.println("(c) = Registrieren");
        System.out.println("(q) = Programm beenden\n");
        System.out.print("Eingabe --> ");
    }
    public void run(){
        String input = "";
        do{
            gibLoginMenueAus();
            try{
                input = eingabeAusgabe.einlesen();
                verarbeiteLoginEingabe(input);
            }catch (IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }while(!input.equals("q"));
    }



    public void verarbeiteLoginEingabe(String line) throws IOException {
            int nutzernummer;
            String name, adresse, passwort;

            switch(line){
                case"a":
                    try{
                    //Kunden Login
                    System.out.print("Geben Sie Ihre Kundennummer ein --> ");
                    nutzernummer = Integer.parseInt(eingabeAusgabe.einlesen());
                    System.out.print("Geben Sie Ihr Passwort ein --> ");
                    passwort = eingabeAusgabe.einlesen();

                    kundenMenue.run();

                    }catch (IOException | NumberFormatException e){ // TODO später in der EA Klasse abfangen
                        System.out.println("Fehler bei der Eingabe");
                    }
                    break;
                case"b":
                    try {

                        //Mitarbeiter Login
                        System.out.print("Geben Sie Ihre Mitarbeiternummer ein --> ");
                        nutzernummer = Integer.parseInt(eingabeAusgabe.einlesen());
                        System.out.print("Geben Sie Ihr Passwort ein --> ");
                        passwort = eingabeAusgabe.einlesen();

                        mitarbeiterMenue.run();
                    }catch (IOException | NumberFormatException e){ // TODO später in der EA Klasse abfangen
                        System.out.println("Fehler bei der Eingabe");
                    }
                    break;
                case"c":
                    try{
                        //Registrierung (nur für Kunden)
                        System.out.println("Registrieren");
                        System.out.println("Geben Sie Ihren Namen ein");
                        name = eingabeAusgabe.einlesen();
                        System.out.println("Geben Sie Ihre Adresse ein");
                        adresse = eingabeAusgabe.einlesen();
                        System.out.println("Geben Sie Ihr Passwort ein");
                        passwort = eingabeAusgabe.einlesen();
                    }catch (IOException | NumberFormatException e){ // TODO später in der EA Klasse abfangen
                        System.out.println("Fehler bei der Eingabe");
                    }
                    break;
                default:
                    break;
            }




    }

}
