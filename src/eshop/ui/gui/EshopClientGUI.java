package eshop.ui.gui;

import eshop.domain.Eshop;
import eshop.ui.gui.panel.ArtikelTablePanel;
import eshop.ui.gui.panel.LoginPanel;
import eshop.ui.gui.panel.SearchArtikelPanel;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EshopClientGUI extends JFrame implements SearchArtikelPanel.SearchResultListener, LoginPanel.LoginListener {
    Eshop shop;

    private ArtikelTablePanel artikelPanel;

    private JInternalFrame loginPanel;

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

        loginPanel = new LoginPanel(shop, this);
        add(loginPanel);

        // MenuBar
        //setJMenuBar(new MenuBarPanel());

        // Tabelle
        artikelPanel = new ArtikelTablePanel(shop.gibAlleArtikel());
        JScrollPane scrollPane = new JScrollPane(artikelPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Suche
        add(new SearchArtikelPanel(this.shop, this), BorderLayout.NORTH);

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
        // TOFO JMenuBar als nächstes
        if(nutzer instanceof Mitarbeiter){
            System.out.println("Mitarbeiter");
        }

        if(nutzer instanceof Kunde){
//            JFrame a = new JFrame("YALLA");
//            a.setVisible(true);
//            a.setSize(400, 400);
        }
    }
}
