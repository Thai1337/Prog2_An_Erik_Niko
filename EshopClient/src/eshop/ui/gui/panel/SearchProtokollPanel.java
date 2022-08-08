package eshop.ui.gui.panel;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.StringConverter;
import eshop.ui.gui.chart.BestandshistorieChart;
import eshop.valueobjects.Protokoll;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SearchProtokollPanel extends JPanel {
    public interface SearchProtokollListener {
        public void onSearchProtokoll(List<Protokoll> artikelList);
    }
    private EshopSerializable shop;
    private SearchProtokollListener searchProtokollListener;

    private JTextField artikelNummerTextField;

    private JButton searchButton;

    public SearchProtokollPanel(EshopSerializable shop, SearchProtokollListener searchProtokollListener) {
        this.shop = shop;
        this.searchProtokollListener = searchProtokollListener;
        setVisible(false);
        initUI();

        setupEvents();
    }

    private void initUI(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;	// Zeile 0

        JLabel suchLabel = new JLabel("Artikelnummer:");
        c.insets = new Insets(2, 5, 2, 2);

        c.gridx = 0;	// Spalte 0
        c.weightx = 0.1;	// 20% der gesamten Breite
        gridBagLayout.setConstraints(suchLabel, c);
        add(suchLabel);

        artikelNummerTextField = new JTextField();
        artikelNummerTextField.setToolTipText("Artikelnummer eingeben.");
        c.gridx = 1;	// Spalte 1
        c.weightx = 0.8;	// 60% der gesamten Breite
        gridBagLayout.setConstraints(artikelNummerTextField, c);
        add(artikelNummerTextField);

        searchButton = new JButton("Verlauf anzeigen");

        c.gridx = 2;	// Spalte 2
        c.weightx = 0.1;	// 20% der gesamten Breite
        gridBagLayout.setConstraints(searchButton, c);
        add(searchButton);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("ProtokollSuche"));
    }

    private void setupEvents() {

        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource().equals(searchButton)) {
                    int suchbegriff = StringConverter.toInteger(artikelNummerTextField.getText());
                    List<Protokoll> suchErgebnis;
                    if (suchbegriff == -1) {
                        try {
                            suchErgebnis = shop.getProtokollListe();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            suchErgebnis = shop.getProtokollNachArtikel(suchbegriff);
                            BestandshistorieChart chart = new BestandshistorieChart("Bestandshistorie", "Bestand über 30 Tage", suchErgebnis, shop, suchbegriff);
                        } catch (ArtikelNichtVorhandenException e) {
                            throw new RuntimeException(e);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Listener benachrichtigen, damit er die Ausgabe aktualisieren kann
                    searchProtokollListener.onSearchProtokoll(suchErgebnis);
                }
            }
        });
    }
}
