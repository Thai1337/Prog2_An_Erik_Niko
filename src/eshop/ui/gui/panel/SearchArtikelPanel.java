package eshop.ui.gui.panel;

import eshop.domain.Eshop;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchArtikelPanel extends JPanel {

    public interface SearchResultListener {
        public void onSearchResult(List<Artikel> artikelList);
    }

    private Eshop shop;
    private SearchResultListener searchListener;

    private JTextField searchTextField;
    private JButton searchButton;

    public SearchArtikelPanel(Eshop shop, SearchResultListener searchListener) {
        this.shop = shop;
        this.searchListener = searchListener;

        initUI();

        setupEvents();
    }

    private void  initUI() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;	// Zeile 0

        JLabel suchLabel = new JLabel("Suchbegriff:");
        c.insets = new Insets(2, 5, 2, 2);

        c.gridx = 0;	// Spalte 0
        c.weightx = 0.1;	// 20% der gesamten Breite
        gridBagLayout.setConstraints(suchLabel, c);
        add(suchLabel);

        searchTextField = new JTextField();
        searchTextField.setToolTipText("Suchbegriff eingeben.");
        c.gridx = 1;	// Spalte 1
        c.weightx = 0.7;	// 60% der gesamten Breite
        gridBagLayout.setConstraints(searchTextField, c);
        add(searchTextField);

        searchButton = new JButton("Suchen");

        c.gridx = 2;	// Spalte 2
        c.weightx = 0.2;	// 20% der gesamten Breite
        gridBagLayout.setConstraints(searchButton, c);
        add(searchButton);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Suche"));
    }

    private void setupEvents() {
        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource().equals(searchButton)) {
                    String suchbegriff = searchTextField.getText();
                    List<Artikel> suchErgebnis;
                    if (suchbegriff.isEmpty()) {
                        suchErgebnis = shop.gibAlleArtikel();
                    } else {
                        suchErgebnis = shop.sucheNachBezeichnung(suchbegriff);
                    }

                    // Listener benachrichtigen, damit er die Ausgabe aktualisieren kann
                    searchListener.onSearchResult(suchErgebnis);
                }
            }
        });
    }

}
