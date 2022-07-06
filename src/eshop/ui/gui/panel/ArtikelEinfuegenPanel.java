package eshop.ui.gui.panel;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
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

public class ArtikelEinfuegenPanel extends JPanel {
    private JTextField bezeichnungTextField;
    private JTextField bestandTextField;
    private JTextField preisTextField;
    private JTextField packungsgroesseTextField;
    private JButton addButton;

    private Eshop shop;

    private Mitarbeiter mitarbeiter;

    private ArtikelEinfuegenListener artikelEinfuegenListener;
    private ArtikelEinfuegenListener protokollArtikelEinfuegenListener;

    public interface ArtikelEinfuegenListener {
        public void onArtikelEinfuegen(List<Artikel> artikelList);
    }

    public ArtikelEinfuegenPanel(Eshop shop, ArtikelEinfuegenListener artikelEinfuegenListener, ArtikelEinfuegenListener protokollArtikelEinfuegenListener) {
        this.shop = shop;
        this.artikelEinfuegenListener = artikelEinfuegenListener;
        this.protokollArtikelEinfuegenListener = protokollArtikelEinfuegenListener;
        initUI();

        setupEvents();
    }

    public void setMitarbeiter(Nutzer nutzer) {
        this.mitarbeiter = (Mitarbeiter) nutzer;
    }

    public void initUI(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        add(new JLabel("Bezeichnung: "));
        bezeichnungTextField = new JTextField();
        add(bezeichnungTextField);
        add(new JLabel("Bestand: "));
        bestandTextField = new JTextField();
        add(bestandTextField);
        add(new JLabel("Artikelpreis: "));
        preisTextField = new JTextField();
        add(preisTextField);
        add(new JLabel("Packungsgroesse: "));
        packungsgroesseTextField = new JTextField();
        add(packungsgroesseTextField);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5,20);
        Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        addButton = new JButton("Einfuegen");
        add(addButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        setVisible(false);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Einfügen"));
    }

    public void setupEvents() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(addButton)){

                    String bezeichnung = bezeichnungTextField.getText();
                    int bestand = StringConverter.toInteger(bestandTextField.getText());
                    double preis = StringConverter.toDouble(preisTextField.getText());
                    int packungsgrosse = StringConverter.toInteger(packungsgroesseTextField.getText());

                    try {
                        shop.fuegeArtikelEin(bezeichnung, bestand, preis, mitarbeiter ,packungsgrosse);
                        artikelEinfuegenListener.onArtikelEinfuegen(shop.gibAlleArtikel());
                        protokollArtikelEinfuegenListener.onArtikelEinfuegen(shop.gibAlleArtikel());
                        setArtikelEinfuegenFieldsToEmpty();
                    } catch (ArtikelExistiertBereitsException | ClassNotFoundException | MassengutartikelBestandsException | IOException | ArtikelbestandUnterNullException | EingabeNichtLeerException ex) {
                        JOptionPane.showMessageDialog(ArtikelEinfuegenPanel.this, ex.getMessage());
                    }
                }
            }
        });
    }

    private void setArtikelEinfuegenFieldsToEmpty() {
        bezeichnungTextField.setText("");
        bestandTextField.setText("");
        preisTextField.setText("");
        packungsgroesseTextField.setText("");
    }
}
