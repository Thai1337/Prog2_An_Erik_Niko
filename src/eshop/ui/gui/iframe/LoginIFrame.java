package eshop.ui.gui.iframe;

import eshop.domain.Eshop;
import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginIFrame extends JInternalFrame {

    public interface LoginListener {
        public void onLogin(Nutzer nutzer);
    }

    private Eshop shop;

    private LoginListener loginListener;

    private LoginListener loginMenuBarListener;

    private JTextField nutzerIDField;

    private JTextField passwortField;

    private JButton loginButton;

    private Nutzer nutzer;

    public LoginIFrame(Eshop shop, LoginListener loginListener, LoginListener loginMenuBarListener) {
        super("Anmelden", false, true, false, false);
        this.shop = shop;
        this.loginListener = loginListener;
        this.loginMenuBarListener = loginMenuBarListener;
        initUI();

        setupEvents();
    }

    private void initUI() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.fill = GridBagConstraints.HORIZONTAL;

        //label options
        gbc.gridx = 0;
        gbc.weightx = 0.2;

        //nummerLabel
        JLabel nummerLabel = new JLabel("Nummer");
        gbc.gridy = 0;
        add(nummerLabel, gbc);

        //passwortLabel
        JLabel passwortLabel = new JLabel("Passwort");
        gbc.gridy = 1;
        add(passwortLabel, gbc);


        //field options
        gbc.gridx = 1;
        gbc.weightx = 0.6;

        //nummerField
        nutzerIDField = new JTextField();
        gbc.gridy = 0;
        add(nutzerIDField, gbc);

        //passwortField
        passwortField = new JPasswordField();
        gbc.gridy = 1;
        add(passwortField, gbc);

        //loginButton
        loginButton = new JButton("Anmelden");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(loginButton, gbc);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(300, 100);

    }

    private void setupEvents() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(loginButton)){
                    String pw = passwortField.getText();
                    int id;
                    try{
                        id = Integer.parseInt(nutzerIDField.getText());
                    }catch (NumberFormatException e1){
                        id = -1;
                    }

                    try{
                        nutzer = shop.mitarbeiterAnmelden(id, pw);
                        if (nutzer == null) {
                            nutzer = shop.kundenAnmelden(id, pw);
                        }
                        if(nutzer != null) {
                            JOptionPane.showMessageDialog(LoginIFrame.this, "Anmeldung erfolgreich");
                            LoginIFrame.this.setVisible(false); // anmeldung erfolgreich login schließen
                            setLoginFieldsToEmpty();
                            loginListener.onLogin(nutzer);
                            loginMenuBarListener.onLogin(nutzer);
                        }

                    }catch (AnmeldungFehlgeschlagenException e2){
                        JOptionPane.showMessageDialog(LoginIFrame.this, e2.getMessage());
                    }

                }
            }
        });
    }

    private void setLoginFieldsToEmpty() {
        nutzerIDField.setText("");
        passwortField.setText("");
    }

}
