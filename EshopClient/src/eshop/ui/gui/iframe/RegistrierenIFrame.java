package eshop.ui.gui.iframe;

import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.StringConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegistrierenIFrame extends JInternalFrame {

    private JTextField nameField;
    private JTextField passwortField;
    private JTextField strasseField;
    private JTextField hausnummerField;
    private JTextField plzField;
    private JTextField ortField;

    private JButton registrierenButton;

    private EshopSerializable shop;

    /**
     *
     * @param shop
     */
    public RegistrierenIFrame(EshopSerializable shop) {
        super("Registrieren", false, true, false, false);
        this.shop = shop;
        initUI();

        setupEvents();
    }

    /*

     */
    private void initUI() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;
        gbc.weighty = 0;


        gbc.gridx = 0; // erste spalte

        JLabel nameLabel = new JLabel("Name");
        gbc.gridy = 0;
        add(nameLabel, gbc);


        JLabel passwortLabel = new JLabel("Passwort");
        gbc.gridy = 1;
        add(passwortLabel, gbc);

        JLabel adresseLabel = new JLabel("Adresse:");
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridy = 2;
        add(adresseLabel, gbc);
        gbc.insets = new Insets(0, 10, 0,0);

        JLabel strasseLabel = new JLabel("Strasse");
        gbc.gridy = 3;
        add(strasseLabel, gbc);

        JLabel hausnummerLabel = new JLabel("Hausnummer");
        gbc.gridy = 4;
        add(hausnummerLabel, gbc);

        JLabel plzLabel = new JLabel("Postleitzahl");
        gbc.gridy = 5;
        add(plzLabel, gbc);

        JLabel ortLabel = new JLabel("Ort");
        gbc.gridy = 6;
        add(ortLabel, gbc);


        gbc.gridx = 1; // zweite spalte

        nameField = new JTextField();
        gbc.gridy = 0;
        add(nameField, gbc);

        passwortField = new JTextField();
        gbc.gridy = 1;
        add(passwortField, gbc);

        strasseField = new JTextField();
        gbc.gridy = 3;
        add(strasseField, gbc);

        hausnummerField = new JTextField();
        gbc.gridy = 4;
        add(hausnummerField, gbc);

        plzField = new JTextField();
        gbc.gridy = 5;
        add(plzField, gbc);

        ortField = new JTextField();
        gbc.gridy = 6;
        add(ortField, gbc);

        registrierenButton = new JButton("Registrieren");
        gbc.gridy = 7;
        add(registrierenButton, gbc);


        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(300, 210);
    }

    /*
     *
     */
    private void setupEvents() {
        registrierenButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(registrierenButton)){
                    String name = nameField.getText();
                    String passwort = passwortField.getText();
                    String strasse = strasseField.getText();
                    int hausnummer = StringConverter.toInteger(hausnummerField.getText());
                    int plz = StringConverter.toInteger(plzField.getText());


                    String ort = ortField.getText();

                    try {
                        int kundenNummer = shop.registriereKunden(name, passwort, strasse, hausnummer, plz, ort);
                        JOptionPane.showMessageDialog(RegistrierenIFrame.this, "Ihre Nummer für den Login lautet: " + kundenNummer);
                        RegistrierenIFrame.this.setVisible(false);
                        setRegistrierenFieldsToEmpty();
                    } catch (EingabeNichtLeerException | IOException ex) {
                        JOptionPane.showMessageDialog(RegistrierenIFrame.this,"Bitte alle Felder ausfuellen!");
                    }
                }
            }
        });
    }

    /*
     *
     */
    private void setRegistrierenFieldsToEmpty() {
        nameField.setText("");
        passwortField.setText("");
        strasseField.setText("");
        hausnummerField.setText("");
        plzField.setText("");
        ortField.setText("");
    }
}
