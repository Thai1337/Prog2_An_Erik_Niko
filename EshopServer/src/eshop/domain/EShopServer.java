package eshop.domain;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EShopServer {

    public EShopServer() {
        Eshop eShop = null;
        String serviceName = "eShopService";
        Registry registry = null;

        try {
            // Dienst-Objekt erzeugen
            eShop = new Eshop();	// Variante mit Serializable-Adressobjekten
            //System.out.println(eShop);

            // Läuft schon eine Registry?
            // (Die könnte z.B. über die Kommandozeile gestartet worden sein.)
            // Registry-Objekt holen...
            registry = LocateRegistry.getRegistry();
            // .. und _versuchen_, unseren Adressbuch-Service zu registrieren:
            registry.rebind(serviceName, eShop);
            // Alternative zu vorangegangener Zeile:
            // Naming.rebind("rmi://localhost:1099/"+serviceName, aBuch);
            // Aber: dann muss MalformedURLException gefangen werden!
            System.out.println("Lokales Registry-Objekt gefunden.");
            System.out.println("E-Shop-Server läuft...");
        } catch (IOException ce) {
            System.out.println(ce);
            // Java liefert ConnectException u.a. dann, wenn zu dem
            // lokalen Registry-Objekt keine echte Registry läuft,
            // d.h. der obige rebind()-Aufruf ist ins Leere gelaufen
            // und damit fehlgeschlagen.
            System.out.println("Registry läuft nicht.");

            try {
                // Dann versuchen, selber eine Registry zu starten:
                // (Achtung, funktioniert nur, wenn die Registry auch alle
                // Klassen aus Common finden kann (CLASSPATH)!)
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("Registry erzeugt.");

                //System.out.println(eShop);
                registry.rebind(serviceName, eShop);
                System.out.println("E-Shop-Server läuft...");

            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                // tritt z.B. auf, wenn Stub-Klasse nicht vorhanden ist
                e.printStackTrace();
            }
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            // tritt z.B. auf, wenn Stub-Klasse nicht vorhanden ist
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EShopServer();
    }
}
