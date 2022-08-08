package eshop.ui.gui.panel;

import eshop.domain.exceptions.ArtikelNichtVorhandenException;
import eshop.domain.exceptions.ArtikelbestandUnterNullException;
import eshop.domain.exceptions.MassengutartikelBestandsException;
import eshop.domain.exceptions.WarenkorbLeerException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.StringConverter;
import eshop.valueobjects.Kunde;
import eshop.valueobjects.Nutzer;
import eshop.valueobjects.Rechnung;
import eshop.valueobjects.Warenkorb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;

public class WarenkorbPanel extends JPanel {
    private JComboBox<String> einfuegenLoeschenComboBox;
    private JButton addButton;
    private JButton rechnungsButton;
    private JTextField mengeTextField;
    private JTextField artikelnummerTextField;

    //private Artikel artikel;
    private EshopSerializable shop;
    private Kunde kunde;
    private String actionCommand;
    private JLabel gesamtpreisLabel;
    private final String[] comboBoxOptions = { "Einfuegen", "Loeschen" }; //col
    public interface ArtikelZuWarenkorbListener {
        public void onArtikelZuWarenkorb() throws RemoteException;
    }
    public interface EinkaufAbschliessenListener {
        public void onEinkaufAbschliessen() throws RemoteException;
    }
    private EinkaufAbschliessenListener einkaufAbschliessenListener;
    private ArtikelZuWarenkorbListener artikelZuWarenkorbListener;
    public WarenkorbPanel(EshopSerializable shop, ArtikelZuWarenkorbListener artikelZuWarenkorbListener, EinkaufAbschliessenListener einkaufAbschliessenListener) {
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

        gbc.insets = new Insets(5, 7, 5,7);

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
        rechnungsButton = new JButton("Einkauf Abschliessen");
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(rechnungsButton, gbc);
        // Rahmen definieren
        //setBorder(BorderFactory.createTitledBorder("Warenkorb"));

        //Gesamtpreis Lable
        gesamtpreisLabel = new JLabel("Gesamtpreis: ");
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(gesamtpreisLabel, gbc);
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
                DecimalFormat df = new DecimalFormat("0.00");
                if(e.getSource().equals(addButton)) {
                    int menge = StringConverter.toInteger(mengeTextField.getText());
                    int nummer = StringConverter.toInteger(artikelnummerTextField.getText());
                    switch(actionCommand) {
                        case "Einfuegen":
                            //if(artikel != null) {
                                try {
                                    Warenkorb warenkorb = shop.artikelZuWarenkorb(nummer, menge, kunde);
                                    kunde.setMeinWarenkorb(warenkorb);
                                    artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                                    setArtikelWarenkorbFieldsToEmpty();
                                } catch (ArtikelbestandUnterNullException | ArtikelNichtVorhandenException | MassengutartikelBestandsException ex) {
                                    JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                                } catch (RemoteException ex) {
                                    throw new RuntimeException(ex);
                                }
                            //}
                            break;
                        case "Loeschen":
                            try {
                                System.out.println("loeschen");
                                // TODO löschen funktioniert nicht mehr richtig(nur die aktualisierung) und das selbe fehlt noch in der zu in bei der Rechnung(alle warenkorbssachen)
                                Warenkorb warenkorb = shop.artikelAusWarenkorbEntfernen(nummer, menge, kunde);
                                kunde.setMeinWarenkorb(warenkorb);
                                artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                                setArtikelWarenkorbFieldsToEmpty();
                            } catch (ArtikelbestandUnterNullException | MassengutartikelBestandsException | ArtikelNichtVorhandenException ex) {
                                JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                            break;
                    }
                    try {
                        gesamtpreisLabel.setText("Gesamtpreis: " + df.format(shop.getWarenkorb(WarenkorbPanel.this.kunde).getGesamtpreis()) + "€");
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        rechnungsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Rechnung rechnung = shop.einkaufAbschliessen(kunde);
                    kunde.getWarkorb().warenkorbLeeren();
                    artikelZuWarenkorbListener.onArtikelZuWarenkorb();
                    einkaufAbschliessenListener.onEinkaufAbschliessen();
                    JOptionPane.showMessageDialog(WarenkorbPanel.this, rechnung);
                    gesamtpreisLabel.setText("Gesamtpreis: ");
                } catch (ArtikelbestandUnterNullException | WarenkorbLeerException | ArtikelNichtVorhandenException | IOException ex) {
                    JOptionPane.showMessageDialog(WarenkorbPanel.this, ex.getMessage());
                }
            }
        });
    }

    private void setArtikelWarenkorbFieldsToEmpty() {
        mengeTextField.setText("Menge");
        mengeTextField.setForeground(Color.GRAY);
        artikelnummerTextField.setText("Artikelnummer");
        artikelnummerTextField.setForeground(Color.GRAY);
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

    public JTextField getArtikelnummerTextField() {
        return artikelnummerTextField;
    }
}
