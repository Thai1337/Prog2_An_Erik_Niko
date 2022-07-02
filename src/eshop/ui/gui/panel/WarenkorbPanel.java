package eshop.ui.gui.panel;

import eshop.domain.Eshop;
import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.domain.exceptions.WarenkorbLeerException;
import eshop.ui.gui.StringConverter;
import eshop.ui.gui.table.ArtikelTable;
import eshop.ui.gui.table.WarenkorbTable;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Nutzer;
import eshop.valueobjects.Rechnung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;

public class WarenkorbPanel extends JPanel {
    private JComboBox<String> einfuegenLoeschenComboBox;
    private JButton addButton;
    private JButton rechungsButton;
    private JTextField mengeTextField;
    private JTextField artikelnummerTextField;

    //private Artikel artikel;
    private Eshop shop;
    private Kunde kunde;
    private String actionCommand;
    private final String[] comboBoxOptions = { "Einfuegen", "Loeschen" }; //col
    public interface ArtikelZuWarenkorbListener {
        public void onArtikelZuWarenkorb();
    }
    public interface EinkaufAbschliessenListener {
        public void onEinkaufAbschliessen();
    }
    private EinkaufAbschliessenListener einkaufAbschliessenListener;
    private ArtikelZuWarenkorbListener artikelZuWarenkorbListener;
    public WarenkorbPanel(Eshop shop, ArtikelZuWarenkorbListener artikelZuWarenkorbListener, EinkaufAbschliessenListener einkaufAbschliessenListener) {
        this.shop = shop;
        this.artikelZuWarenkorbListener = artikelZuWarenkorbListener;
        this.einkaufAbschliessenListener = einkaufAbschliessenListener;
        initUI();

        setupEvents();
    }

    public void initUI() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.1;
        gbc.weighty = 0;

        gbc.gridy = 0; // erste spalte

        //comboBox
        einfuegenLoeschenComboBox = new JComboBox<>(comboBoxOptions);
        gbc.gridy = 1; // erste spalte
        gbc.gridx = 0;
        add(einfuegenLoeschenComboBox, gbc);

        //Artikelnummer TextField
        gbc.weightx = 0.7;
        artikelnummerTextField = new JTextField();
        artikelnummerTextField.setPreferredSize(new Dimension(80,18));
        gbc.gridy = 1; // erste spalte
        gbc.gridx = 1;

        //Artikelnummer Placeholder
        artikelnummerTextField.setText("Artikelnummer");
        artikelnummerTextField.setForeground(Color.GRAY);
        artikelnummerTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent  e) {
                if (artikelnummerTextField.getText().equals("Artikelnummer")) {
                    artikelnummerTextField.setText("");
                    artikelnummerTextField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (artikelnummerTextField.getText().isEmpty()) {
                    artikelnummerTextField.setForeground(Color.GRAY);
                    artikelnummerTextField.setText("Artikelnummer");
                }
            }
        });
        add(artikelnummerTextField, gbc);

        //Menge TextField
        gbc.weightx = 0.7;
        mengeTextField = new JTextField();
        mengeTextField.setPreferredSize(new Dimension(80,18));
        gbc.gridy = 1; // erste spalte
        gbc.gridx = 2;

        //Menge Placeholder
        mengeTextField.setText("Menge");
        mengeTextField.setForeground(Color.GRAY);
        mengeTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent  e) {
                if (mengeTextField.getText().equals("Menge")) {
                    mengeTextField.setText("");
                    mengeTextField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (mengeTextField.getText().isEmpty()) {
                    mengeTextField.setForeground(Color.GRAY);
                    mengeTextField.setText("Menge");
                }
            }
        });
        add(mengeTextField, gbc);

        //Button
        gbc.weightx = 0.1;
        addButton = new JButton("Bestaetigen");
        gbc.gridx = 3;
        add(addButton, gbc);

        //Rechungs Button
        gbc.weightx = 0.1;
        rechungsButton = new JButton("Einkauf Abschliessen");
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(rechungsButton, gbc);
        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Warenkorb"));

        setVisible(false);
    }

    public void setupEvents() {
        actionCommand = "Einfuegen";
        einfuegenLoeschenComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox o = (JComboBox) e.getSource();
                actionCommand = (String)o.getSelectedItem();
            }
        });

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource().equals(addButton)) {
                    int menge = StringConverter.toInteger(mengeTextField.getText());
                    int nummer = StringConverter.toInteger(artikelnummerTextField.getText());
                    switch(actionCommand) {
                        case "Einfuegen":
                            //if(artikel != null) {
                                try {
                                    shop.artikelZuWarenkorb(nummer, menge, kunde);
                                    artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                                } catch (ArtikelbestandUnterNullException | ArtikelNichtVorhandenException | MassengutartikelBestandsException ex) {
                                    JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                                }
                            //}
                            break;
                        case "Loeschen":
                            try {
                                System.out.println("löschen");
                                shop.artikelAusWarenkorbEntfernen(nummer, menge, kunde);
                                artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                            } catch (ArtikelbestandUnterNullException | MassengutartikelBestandsException | ArtikelNichtVorhandenException ex) {
                                JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                            }
                            break;
                    }
                }
            }
        });

        rechungsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Rechnung rechung = shop.einkaufAbschliessen(kunde);
                    artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                    einkaufAbschliessenListener.onEinkaufAbschliessen();
                    JOptionPane.showMessageDialog(WarenkorbPanel.this, rechung);
                } catch (ArtikelbestandUnterNullException | WarenkorbLeerException | ArtikelNichtVorhandenException | IOException ex) {
                    JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                }
            }
        });
    }

    private void setArtikelWarenkorbFieldsToEmpty() {
        mengeTextField.setText("");
    }

//    @Override
//    public void onMouseClick(Kunde kunde, Artikel artikel) {
//        this.kunde = kunde;
//        this.artikel = artikel;
//    }

    public void setKunde(Nutzer kunde) {
        this.kunde = (Kunde) kunde;
        setVisible(true);
    }
}
