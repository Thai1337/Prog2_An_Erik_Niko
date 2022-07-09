package eshop.ui.gui;

import eshop.domain.Eshop;
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
import org.w3c.dom.events.EventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.List;

public class EshopClientGUI extends JFrame
        implements SearchArtikelPanel.SearchResultListener, LoginIFrame.LoginListener,
        KontoMenu.LoginMenuItemClickListener, KontoMenu.RegistrierenMenuItemClickListener, KontoMenu.LogoutMenuItemClickListener,
        ArtikelMenu.ArtikelEinfuegenItemClickListener, ArtikelEinfuegenPanel.ArtikelEinfuegenListener, ArtikelLoeschenPanel.ArtikelLoeschenListener, ArtikelMenu.ArtikelLoeschenItemClickListener,
        MitarbeiterMenu.MitarbeiterHinzufuegenItemClickListener, SearchProtokollPanel.SearchProtokollListener {
    private Eshop shop;

    private ArtikelTable artikelTable;
    private WarenkorbTable warenkorbTable;
    private ProtokollTable protokollTable;
    private WarenkorbPanel warenkorbPanel;
    private JInternalFrame loginFrame;
    private JInternalFrame registrierenFrame;

    private ArtikelMenu artikelMenu;

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
    public EshopClientGUI(String title) {
            super(title);

            try {
                shop = new Eshop();
                initGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void initGUI() {

       //Layout des JFrames
        setLayout(new BorderLayout());

        // registrierenIFrame
        registrierenFrame = new RegistrierenIFrame(shop);
        add(registrierenFrame);

        // MenuBar
        JMenuBar menuBar = new JMenuBar();
        kontoMenu = new KontoMenu("Konto", this, this, this);
        artikelMenu = new ArtikelMenu("Artikel", this, this);
        mitarbeiterMenu = new MitarbeiterMenu("Mitarbeiter", this);
        menuBar.add(kontoMenu);
        menuBar.add(artikelMenu);
        menuBar.add(mitarbeiterMenu);
        setJMenuBar(menuBar);

        // loginIFrame
        loginFrame = new LoginIFrame(shop, this, kontoMenu, artikelMenu, mitarbeiterMenu); // konto menu ist auch ein loginListener
        add(loginFrame);

        // Tabellen
        warenkorbTable = new WarenkorbTable(shop);
        protokollTable = new ProtokollTable(shop);
        artikelTable = new ArtikelTable(shop, protokollTable);
        warenkorbPanel = new WarenkorbPanel(shop, warenkorbTable, artikelTable);

        artikelTable.setArtikelnummerTextField(warenkorbPanel.getArtikelnummerTextField());
        warenkorbTable.setArtikelnummerTextField(warenkorbPanel.getArtikelnummerTextField());

        add(warenkorbPanel, BorderLayout.SOUTH);

        tabs = new JTabbedPane();
        tabs.addTab("Artikel", new JScrollPane(artikelTable));

        add(tabs, BorderLayout.CENTER);

        // Suche
        JLayeredPane suchePane = new JLayeredPane();
        suchePane.setLayout(new BoxLayout(suchePane, BoxLayout.Y_AXIS));

        artikelSearchPanel = new SearchArtikelPanel(this.shop, this);
        suchePane.add(artikelSearchPanel);
        //add(artikelSearchPanel, BorderLayout.NORTH);
        protokollSearchPanel = new SearchProtokollPanel(this.shop, this);
        suchePane.add(protokollSearchPanel);
        add(suchePane, BorderLayout.NORTH);

        suchePane.setVisible(true);



        //Einfuegen Panel
        artikelEinfuegenPanel = new ArtikelEinfuegenPanel(shop, this, protokollTable);

        //Loeschen Panel
        artikelLoeschenPanel = new ArtikelLoeschenPanel(shop, this, protokollTable);

        //Mitarbeiter Hinzufuegen Panel
        mitarbeiterHinzufuegenPanel = new MitarbeiterHinzufuegenPanel(shop);
        add(mitarbeiterHinzufuegenPanel, BorderLayout.EAST);


        //Loeschen und Einfuegen Panel
        JLayeredPane layeredPane2 = new JLayeredPane();
        layeredPane2.setLayout(new BoxLayout(layeredPane2, BoxLayout.Y_AXIS));
        layeredPane2.add(artikelEinfuegenPanel, JLayeredPane.POPUP_LAYER);
        layeredPane2.add(artikelLoeschenPanel, JLayeredPane.POPUP_LAYER);
        add(layeredPane2, BorderLayout.WEST);
        layeredPane2.setSize(300, 480);
        layeredPane2.setVisible(true);

        // Ob das SearchArtikelPanel oder das SearchProtokollPanel angezeigt wird
        setupTabEvents();

        //JFrame optionen
        setSize(1040, 580);
        setVisible(true);

    }

    private void setupTabEvents(){
        tabs.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if(nutzer != null && nutzer instanceof Mitarbeiter){
                    if(tabs.getSelectedIndex() == 0) {
                        artikelSearchPanel.setVisible(true);
                        protokollSearchPanel.setVisible(false);
                    }else{
                        artikelSearchPanel.setVisible(false);
                        protokollSearchPanel.setVisible(true);
                    }
                }

            }
        });
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EshopClientGUI gui = new EshopClientGUI("Eshop");
            }
        });
    }

    @Override
    public void onSearchResult(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    @Override
    public void onSearchProtokoll(List<Protokoll> protokollList) {
        protokollTable.updateProtokoll(protokollList);
    }

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

    @Override
    public void onLoginMenuItemClick() {
        loginFrame.setVisible(true);
    }

    @Override
    public void onRegistrierenMenuItemClick() {
        registrierenFrame.setVisible(true);
    }

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

    @Override
    public void onArtikelEinfuegenMenuItemClick(boolean sichtbar) {
        artikelEinfuegenPanel.setVisible(sichtbar);
    }

   public void onArtikelLoeschenMenuItemClick(boolean sichtbar) {
        artikelLoeschenPanel.setVisible(sichtbar);
   }

    @Override
    public void onArtikelEinfuegen(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    @Override
    public void onArtikelLoeschen(List<Artikel> artikelList) {
        artikelTable.updateArtikel(artikelList);
    }

    @Override
    public void onMitarbeiterHinzufuegenMenuItemClick(boolean sichtbar) {
        mitarbeiterHinzufuegenPanel.setVisible(sichtbar);
    }


}
