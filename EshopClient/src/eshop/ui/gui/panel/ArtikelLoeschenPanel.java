package eshop.ui.gui.panel;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.net.rmi.common.eShopSerializable;
import eshop.ui.gui.StringConverter;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Mitarbeiter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ArtikelLoeschenPanel extends JPanel {
    private JTextField nummerTextField;
    private JButton deleteButton;

    private Mitarbeiter mitarbeiter;
    private eShopSerializable shop;
    private ArtikelLoeschenListener artikelLoeschenListener;
    private ArtikelLoeschenListener protokollArtikelLoeschenListener;

    public interface ArtikelLoeschenListener {
        public void onArtikelLoeschen(List<Artikel> artikelList);
    }

    public ArtikelLoeschenPanel(eShopSerializable shop, ArtikelLoeschenListener artikelLoeschenListener, ArtikelLoeschenListener protokollArtikelLoeschenListener) {
        this.shop = shop;
        this.artikelLoeschenListener = artikelLoeschenListener;
        this.protokollArtikelLoeschenListener = protokollArtikelLoeschenListener;
        initUI();

        setupEvents();
    }

    public void setMitarbeiter(Nutzer nutzer) {
        this.mitarbeiter = (Mitarbeiter) nutzer;
    }

    private void initUI(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        add(new JLabel("Artikelnummer: "));
        nummerTextField = new JTextField();
        add(nummerTextField);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5,20);
        Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        deleteButton = new JButton("Löschen");
        add(deleteButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        setVisible(false);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Löschen"));
        BorderFactory.createTitledBorder("").setTitleColor(Color.RED);
    }

    public void setupEvents() {
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(deleteButton)){

                    int artikelNummer = StringConverter.toInteger(nummerTextField.getText());

                    try {
                        shop.loescheArtikel(artikelNummer, mitarbeiter);
                        artikelLoeschenListener.onArtikelLoeschen(shop.gibAlleArtikel());
                        protokollArtikelLoeschenListener.onArtikelLoeschen(shop.gibAlleArtikel());
                        nummerTextField.setText("");
                    } catch (IOException | ArtikelNichtVorhandenException ex) {
                        JOptionPane.showMessageDialog(ArtikelLoeschenPanel.this, ex.getMessage());
                    }
                }
            }
        });
    }
}
