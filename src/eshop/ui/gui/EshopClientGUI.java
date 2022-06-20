package eshop.ui.gui;

import eshop.domain.Eshop;
import eshop.ui.gui.panel.ArtikelTablePanel;
import eshop.ui.gui.iframe.LoginIFrame;
import eshop.ui.gui.menu.MenuBarPanel;
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
        MenuBarPanel.LoginMenuItemClickListener, MenuBarPanel.RegistrierenMenuItemClickListener, MenuBarPanel.LogoutMenuItemClickListener {
    private Eshop shop;

    private ArtikelTablePanel artikelPanel;

    private JInternalFrame loginFrame;
    private JInternalFrame registrierenFrame;

    private JMenuBar menuBar;

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

        // loginIFrame
        loginFrame = new LoginIFrame(shop, this);
        add(loginFrame);

        // registrierenIFrame
        registrierenFrame = new RegistrierenIFrame(shop);
        add(registrierenFrame);

        // MenuBar
        menuBar = new MenuBarPanel(this, this, this);
        setJMenuBar(menuBar);

        // Tabelle
        artikelPanel = new ArtikelTablePanel(shop.gibAlleArtikel());
        add(new JScrollPane(artikelPanel), BorderLayout.CENTER);

        // Suche
        add(new SearchArtikelPanel(this.shop, this), BorderLayout.NORTH);

        //JFrame optionen
        setSize(640, 480);
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
            System.out.println("Nutzer ungleich nulll");

            // TODO in methode packen
            menuBar.getMenu(0).getMenuComponent(0).setVisible(false);   //loginItem invisable
            menuBar.getMenu(0).getMenuComponent(1).setVisible(false);   // seperator invisable
            menuBar.getMenu(0).getMenuComponent(2).setVisible(false);   //registrierenItem invisable

            menuBar.getMenu(0).getMenuComponent(3).setVisible(true);   //logoutItem visable
        }

        if(nutzer instanceof Mitarbeiter){
            System.out.println("Mitarbeiter ist eingeloggt");
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
    }
}
