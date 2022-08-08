package eshop.ui.gui;

import eshop.net.events.ArtikelListeChangedEventListener;
import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.menu.ArtikelMenu;
import eshop.ui.gui.menu.KontoMenu;
import eshop.ui.gui.menu.MitarbeiterMenu;
import eshop.ui.gui.panel.*;
import eshop.ui.gui.iframe.LoginIFrame;
import eshop.ui.gui.iframe.RegistrierenIFrame;
import eshop.ui.gui.table.ArtikelTable;
import eshop.ui.gui.table.ProtokollTable;
import eshop.ui.gui.table.WarenkorbTable;
import eshop.valueobjects.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Klasse für grafische Benutzungsschnittstelle des
 * E-Shops. Die Benutzungsschnittstelle basiert auf Swing, daher der Name GUI
 * (Graphical User Interface).
 */
public class EshopClientGUI extends UnicastRemoteObject
        implements SearchArtikelPanel.SearchResultListener, LoginIFrame.LoginListener,
        KontoMenu.LoginMenuItemClickListener, KontoMenu.RegistrierenMenuItemClickListener, KontoMenu.LogoutMenuItemClickListener,
        ArtikelMenu.ArtikelEinfuegenItemClickListener, ArtikelEinfuegenPanel.ArtikelEinfuegenListener, ArtikelLoeschenPanel.ArtikelLoeschenListener, ArtikelMenu.ArtikelLoeschenItemClickListener,
        MitarbeiterMenu.MitarbeiterHinzufuegenItemClickListener, SearchProtokollPanel.SearchProtokollListener, ArtikelListeChangedEventListener, Serializable {
    private EshopSerializable shop;
    private static final long serialVersionUID = 109622345876664424L;
    private ArtikelTable artikelTable;
    private WarenkorbTable warenkorbTable;
    private ProtokollTable protokollTable;
    private WarenkorbPanel warenkorbPanel;
    private JInternalFrame loginFrame;
    private JInternalFrame registrierenFrame;

    private ArtikelMenu artikelMenu;
    private static int DEFAULT_PORT = 1099;
    private KontoMenu kontoMenu;
    private MitarbeiterMenu mitarbeiterMenu;

    private ArtikelEinfuegenPanel artikelEinfuegenPanel;
    private SearchArtikelPanel artikelSearchPanel;
    private SearchProtokollPanel protokollSearchPanel;
    private ArtikelLoeschenPanel artikelLoeschenPanel;
    private MitarbeiterHinzufuegenPanel mitarbeiterHinzufuegenPanel;
    //private JDialog jdialog;
    private JTabbedPane tabs;

    private Nutzer nutzer;

    private JFrame mainFrame;

    /**
     * Konstruktor welcher den Clientcode für RMI enthält.
     *
     * @param title
     * @throws RemoteException
     */
    public EshopClientGUI(String title) throws RemoteException {
        super();
        mainFrame = new JFrame(title);

        String serviceName = "eShopService";
        String host = "localhost";
        int port = DEFAULT_PORT;
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            this.shop = (EshopSerializable) registry.lookup(serviceName); // Variante mit Serializable

            this.shop.addEventListener(this);
        }

        catch (NotBoundException e) {
            // unter der URL ist kein RMI-Objekt registriert
            e.printStackTrace();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            initGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *  Methode zum Initialisieren der GUI
     *
     * @throws RemoteException
     */
    private void initGUI() throws RemoteException {

       //Layout des JFrames
        mainFrame.setLayout(new BorderLayout());

        // registrierenIFrame
        registrierenFrame = new RegistrierenIFrame(shop);
        mainFrame.add(registrierenFrame);

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        kontoMenu = new KontoMenu("Konto", this, this, this);
        artikelMenu = new ArtikelMenu("Artikel", this, this);
        mitarbeiterMenu = new MitarbeiterMenu("Mitarbeiter", this);
        menuBar.add(kontoMenu);
        menuBar.add(artikelMenu);
        menuBar.add(mitarbeiterMenu);
        mainFrame.setJMenuBar(menuBar);

        // loginIFrame
        loginFrame = new LoginIFrame(shop, this, kontoMenu, artikelMenu, mitarbeiterMenu); // konto menu ist auch ein loginListener
        mainFrame.add(loginFrame);

        // Tabellen
        warenkorbTable = new WarenkorbTable(shop);
        protokollTable = new ProtokollTable(shop);
        artikelTable = new ArtikelTable(shop, protokollTable);
        warenkorbPanel = new WarenkorbPanel(shop, warenkorbTable, artikelTable);

        artikelTable.setArtikelnummerTextField(warenkorbPanel.getArtikelnummerTextField());
        warenkorbTable.setArtikelnummerTextField(warenkorbPanel.getArtikelnummerTextField());

        mainFrame.add(warenkorbPanel, BorderLayout.SOUTH);

        // Tabs
        tabs = new JTabbedPane();
        tabs.addTab("Artikel", new JScrollPane(artikelTable));

        mainFrame.add(tabs, BorderLayout.CENTER);

        // Suche
        JLayeredPane suchePane = new JLayeredPane();
        suchePane.setLayout(new BoxLayout(suchePane, BoxLayout.Y_AXIS));

        artikelSearchPanel = new SearchArtikelPanel(this.shop, this);
        suchePane.add(artikelSearchPanel);
        //add(artikelSearchPanel, BorderLayout.NORTH);
        protokollSearchPanel = new SearchProtokollPanel(this.shop, this);
        suchePane.add(protokollSearchPanel);
        mainFrame.add(suchePane, BorderLayout.NORTH);

        suchePane.setVisible(true);



        //Einfuegen Panel
        artikelEinfuegenPanel = new ArtikelEinfuegenPanel(shop, this, protokollTable);

        //Loeschen Panel
        artikelLoeschenPanel = new ArtikelLoeschenPanel(shop, this, protokollTable);

        //Mitarbeiter Hinzufuegen Panel
        mitarbeiterHinzufuegenPanel = new MitarbeiterHinzufuegenPanel(shop);
        mainFrame.add(mitarbeiterHinzufuegenPanel, BorderLayout.EAST);


        //Loeschen und Einfuegen Panel
        JLayeredPane layeredPane2 = new JLayeredPane();
        layeredPane2.setLayout(new BoxLayout(layeredPane2, BoxLayout.Y_AXIS));
        layeredPane2.add(artikelEinfuegenPanel, JLayeredPane.POPUP_LAYER);
        layeredPane2.add(artikelLoeschenPanel, JLayeredPane.POPUP_LAYER);
        mainFrame.add(layeredPane2, BorderLayout.WEST);
        layeredPane2.setSize(300, 480);
        layeredPane2.setVisible(true);

        // Ob das SearchArtikelPanel oder das SearchProtokollPanel angezeigt wird
        setupTabEvents();

        //JFrame optionen
        mainFrame.setSize(1040, 580);
        mainFrame.setVisible(true);
    }

    /**
     *  Events für die JTabbedPanes um entweder die Artikelsuchleiste oder die Protokollsuchleiste anzuzeigen.
     */
    private void setupTabEvents(){
        tabs.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(nutzer != null && nutzer instanceof Mitarbeiter){ //null abfrage, weil man unangemeldet ist, kann man immer noch auf den ersten Tab drücken.
                    if(tabs.getSelectedIndex() == 0) { //Artikeltab -> Artikelsuchleiste wird angezeigt
                        artikelSearchPanel.setVisible(true);
                        protokollSearchPanel.setVisible(false);
                    }else{// !=0 Protokolltab -> Protokollsuchleiste wird angezeigt
                        artikelSearchPanel.setVisible(false);
                        protokollSearchPanel.setVisible(true);
                    }
                }

            }
        });
    }

    /**
     * Start des Programms
     * @param args
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    EshopClientGUI gui = new EshopClientGUI("Eshop");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Update der Artikellist beim Betätigen des Suchen Button
     * @param artikelList übergibt die Artikelliste
     */
    @Override
    public void onSearchResult(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    /**
     * Update der ProtokollList beim Betätigen des Verlauf anzeigen Button
     * @param protokollList übergibt die Protokollliste
     */
    @Override
    public void onSearchProtokoll(List<Protokoll> protokollList) {
        protokollTable.updateProtokoll(protokollList);
    }

    /**
     * Funktion zum Unterscheiden vom User beim Login. Des Weiteren
     * wird bestimmt, welche Gui Elemente für den Nutzer angezeigt werden sollen.
     * @param nutzer gibt den Nutzer weiter
     */
    @Override
    public void onLogin(Nutzer nutzer) {
        this.nutzer = nutzer;
        // TODO ab hier kann man mit der nutzernummer weiterarbeiten oder vllt zwei interfaces für Mitarbeiter und kundentrennung implementieren
        // TODO JMenuBar als nächstes

        if(nutzer instanceof Mitarbeiter){
            artikelEinfuegenPanel.setMitarbeiter(nutzer);
            artikelLoeschenPanel.setMitarbeiter(nutzer);
            artikelTable.setMitarbeiter(nutzer);
            protokollTable.setMitarbeiter(nutzer);
            tabs.addTab("Protokoll", new JScrollPane(protokollTable));
        }

        if(nutzer instanceof Kunde){
            warenkorbPanel.setKunde(nutzer);
            warenkorbTable.setKunde(nutzer);
            tabs.addTab("Warenkorb", new JScrollPane(warenkorbTable));
        }
    }

    /**
     * Methode zum Anzeigen des Login Pop-ups bei dem click auf den Button
     */
    @Override
    public void onLoginMenuItemClick() {
        loginFrame.setVisible(true);
    }

    /**
     *Methode zum Anzeigen des Registrieren Pop-ups bei dem click auf den Button
     */
    @Override
    public void onRegistrierenMenuItemClick() {
        registrierenFrame.setVisible(true);
    }

    /**
     *Methode zum Ausblenden der Gui Elemente auf den der unangemeldete User keinen Zugriff hat, bei dem click auf den Button
     */
    @Override
    public void onLogoutMenuItemClick() {
        artikelEinfuegenPanel.setVisible(false);
        artikelLoeschenPanel.setVisible(false);
        artikelMenu.setVisible(false);
        artikelTable.setIstMitarbeiterAngemeldet(false);
        mitarbeiterMenu.setVisible(false);
        mitarbeiterHinzufuegenPanel.setVisible(false);
        warenkorbPanel.setVisible(false);
        tabs.remove(1);
    }

    /**
     *Methode zum Anzeigen des Einfügen-Panels bei dem click auf den Button in der Menu Bar
     * @param sichtbar boolischer Wert welcher bei jedem click zwischen True und false wechselt
     */
    @Override
    public void onArtikelEinfuegenMenuItemClick(boolean sichtbar) {
        artikelEinfuegenPanel.setVisible(sichtbar);
    }

    /**
     *Methode zum Anzeigen des Löschen-Panels bei dem click auf den Button in der Menu Bar
     * @param sichtbar boolischer Wert welcher bei jedem click zwischen True und false wechselt
     */
   public void onArtikelLoeschenMenuItemClick(boolean sichtbar) {
        artikelLoeschenPanel.setVisible(sichtbar);
   }

    /**
     *Methode, welche die Artikeltabelle aktualisiert, sobald ein neuer Artikel hinzugefügt wurde.
     * @param artikelList Liste der Artikel
     */
    @Override
    public void onArtikelEinfuegen(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    /**
     *Methode, welche die Artikeltabelle aktualisiert, sobald ein Artikel gelöscht wurde.
     * @param artikelList Liste der Artikel
     */
    @Override
    public void onArtikelLoeschen(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    /**
     *Methode zum Anzeigen des Mitarbeiter-Panels bei dem click auf den Button in der Menu Bar
     * @param sichtbar Boolischer Wert, welcher bei jedem Klick zwischen true und false wechselt
     */
    @Override
    public void onMitarbeiterHinzufuegenMenuItemClick(boolean sichtbar) {
        mitarbeiterHinzufuegenPanel.setVisible(sichtbar);
    }

    /**
     * Methode, dass wenn ein anderer Client eine Änderung in der Artikelliste vornimmt.
     * Wird bei den anderen Client die ArtikelTable automatisch aktualisiert.
     * @param artikelList die aktualisierte Artikelliste wird übergeben
     */
    @Override
    public void onArtikelListeChangedEvent(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }
}
