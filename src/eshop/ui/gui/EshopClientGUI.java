package eshop.ui.gui;

import eshop.domain.Eshop;
import eshop.ui.gui.menu.ArtikelMenu;
import eshop.ui.gui.menu.KontoMenu;
import eshop.ui.gui.panel.ArtikelEinfuegenPanel;
import eshop.ui.gui.panel.ArtikelLoeschenPanel;
import eshop.ui.gui.panel.ArtikelTablePanel;
import eshop.ui.gui.iframe.LoginIFrame;
import eshop.ui.gui.iframe.RegistrierenIFrame;
import eshop.ui.gui.panel.SearchArtikelPanel;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EshopClientGUI extends JFrame
        implements SearchArtikelPanel.SearchResultListener, LoginIFrame.LoginListener,
        KontoMenu.LoginMenuItemClickListener, KontoMenu.RegistrierenMenuItemClickListener, KontoMenu.LogoutMenuItemClickListener,
        ArtikelMenu.ArtikelEinfuegenItemClickListener, ArtikelEinfuegenPanel.ArtikelEinfuegenListener, ArtikelLoeschenPanel.ArtikelLoeschenListener, ArtikelMenu.ArtikelLoeschenItemClickListener{
    private Eshop shop;

    private ArtikelTablePanel artikelPanel;

    private JInternalFrame loginFrame;
    private JInternalFrame registrierenFrame;

    private ArtikelMenu artikelMenu;

    private KontoMenu kontoMenu;

    private ArtikelEinfuegenPanel artikelEinfuegenPanel;

    private ArtikelLoeschenPanel artikelLoeschenPanel;

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
        menuBar.add(kontoMenu);
        menuBar.add(artikelMenu);
        setJMenuBar(menuBar);

        // loginIFrame
        loginFrame = new LoginIFrame(shop, this, kontoMenu, artikelMenu); // konto menu ist auch ein loginListener
        add(loginFrame);

        // Tabelle
        artikelPanel = new ArtikelTablePanel(shop);
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);

        // Suche
        add(new SearchArtikelPanel(this.shop, this), BorderLayout.NORTH);

        //Einfuegen Panel
        artikelEinfuegenPanel = new ArtikelEinfuegenPanel(shop, this);

        //Loeschen Panel
        artikelLoeschenPanel = new ArtikelLoeschenPanel(shop, this);

        //Loeschen und Einfuegen Panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BoxLayout(layeredPane, BoxLayout.Y_AXIS));
        layeredPane.add(artikelLoeschenPanel, JLayeredPane.POPUP_LAYER);
        layeredPane.add(artikelEinfuegenPanel, JLayeredPane.POPUP_LAYER);
        add(layeredPane, BorderLayout.WEST);
        layeredPane.setSize(300, 480);
        layeredPane.setVisible(true);



        //JFrame optionen
        setSize(840, 580);
        setVisible(true);

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
        artikelPanel.updateArtikel(artikelList);
    }

    @Override
    public void onLogin(Nutzer nutzer) {
        // TODO ab hier kann man mit der nutzernummer weiterarbeiten oder vllt zwei interfaces für Mitarbeiter und kundentrennung implementieren
        // TODO JMenuBar als nächstes
        if(nutzer != null) {
            System.out.println("Kunde oder Mitarbeiter eingeloggt");
        }

        if(nutzer instanceof Mitarbeiter){
            System.out.println("Mitarbeiter ist eingeloggt");
            artikelEinfuegenPanel.setMitarbeiter(nutzer);
            artikelLoeschenPanel.setMitarbeiter(nutzer);
            artikelPanel.setMitarbeiter(nutzer);
        }

        if(nutzer instanceof Kunde){
            System.out.println("Kunde ist eingeloggt");
        }
    }

    @Override
    public void onLoginMenuItemClick() {
        System.out.println("LoginFrame erscheint");
        loginFrame.setVisible(true);
    }

    @Override
    public void onRegistrierenMenuItemClick() {
        System.out.println("RegFrame erscheint");
        registrierenFrame.setVisible(true);
    }

    @Override
    public void onLogoutMenuItemClick() {
        System.out.println("Kunden und Mitarbeiter Panels und Co. verschwinden");
        artikelEinfuegenPanel.setVisible(false);
        artikelLoeschenPanel.setVisible(false);
        artikelMenu.setVisible(false);
    }

    @Override
    public void onArtikelEinfuegenMenuItemClick(boolean sichtbar) {
        System.out.println("Einfuegen Panel erscheint");
        artikelEinfuegenPanel.setVisible(sichtbar);
    }

   public void onArtikelLoeschenMenuItemClick(boolean sichtbar) {
        System.out.println("Löschen Panel erscheint");
        artikelLoeschenPanel.setVisible(sichtbar);
   }

    @Override
    public void onArtikelEinfuegen(List<Artikel> artikelList) {
        artikelPanel.updateArtikel(artikelList);
    }

    @Override
    public void onArtikelLoeschen(List<Artikel> artikelList) {
        artikelPanel.updateArtikel(artikelList);
    }
}
