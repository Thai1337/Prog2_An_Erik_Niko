package eshop.ui.gui.panel;

import eshop.domain.exceptions.EingabeNichtLeerException;
import eshop.net.rmi.common.eShopSerializable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MitarbeiterHinzufuegenPanel extends JPanel {
    private JTextField mitarbeiterNameTextField;
    private JTextField mitarbeiterPasswortTextField;
    private JButton addButton;

    private eShopSerializable shop;
    public MitarbeiterHinzufuegenPanel(eShopSerializable shop) {
        this.shop = shop;

        initUI();

        setupEvents();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));


        add(new JLabel("Name: "));
        mitarbeiterNameTextField = new JTextField();
        add(mitarbeiterNameTextField);
        add(new JLabel("Passwort: "));
        mitarbeiterPasswortTextField = new JTextField();
        add(mitarbeiterPasswortTextField);

        // Abstandhalter ("Filler") zwischen letztem Eingabefeld und Add-Button
        Dimension fillerMinSize = new Dimension(5,20);
        Dimension fillerPreferredSize = new Dimension(5,Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5,Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        addButton = new JButton("Einstellen");
        add(addButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));
        setVisible(false);

        // Rahmen definieren
        setBorder(BorderFactory.createTitledBorder("Einstellen"));

    }

    public void setupEvents() {
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(addButton)) {
                    int nummer;
                    String name = mitarbeiterNameTextField.getText();
                    String passwort = mitarbeiterPasswortTextField.getText();

                    try {
                        nummer = shop.erstelleMitarbeiter(name, passwort);
                        setMitarbeiterEinstellenFieldsToEmpty();
                        JOptionPane.showMessageDialog(MitarbeiterHinzufuegenPanel.this, "Die Nummer des neuen Mitarbeiters lautet: " + nummer);
                    } catch (EingabeNichtLeerException | IOException ex) {
                        JOptionPane.showMessageDialog(MitarbeiterHinzufuegenPanel.this, ex.getMessage());
                    }
                }
            }
        });
    }

    private void setMitarbeiterEinstellenFieldsToEmpty() {
        mitarbeiterNameTextField.setText("");
        mitarbeiterPasswortTextField.setText("");
    }
}
