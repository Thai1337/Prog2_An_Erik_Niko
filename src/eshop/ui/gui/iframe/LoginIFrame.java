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

    private JLabel nummerLabel;

    private JLabel passwortLabel;

    private JTextField nutzerIDField;

    private JTextField passwortField;

    private JButton loginButton;

    private Nutzer nutzer;

    public LoginIFrame(Eshop shop, LoginListener loginListener) {
        super("Login", false, true, false, false);
        this.shop = shop;
        this.loginListener = loginListener;
        initUI();

        setupEvents();
    }

    private void initUI() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.fill = GridBagConstraints.HORIZONTAL;

        //nummerLabel
        nummerLabel = new JLabel("Nummer");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        add(nummerLabel, gbc);

        //nummerField
        nutzerIDField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        add(nutzerIDField, gbc);

        //passwortLabel
        passwortLabel = new JLabel("Passwort");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        add(passwortLabel, gbc);

        //passwortField
        passwortField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        add(passwortField, gbc);

        //loginButton
        loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(loginButton, gbc);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(300, 100);
        //setVisible(true);

    }

    private void setupEvents() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(loginButton)){
                    String pw = passwortField.getText();
                    try{
                        int id = Integer.parseInt(nutzerIDField.getText());

                        nutzer = shop.mitarbeiterAnmelden(id, pw);
                        if (nutzer == null) {
                            nutzer = shop.kundenAnmelden(id, pw);
                        }
                        if(nutzer != null) {
                            JOptionPane.showMessageDialog(LoginIFrame.this, "Anmeldung erfolgreich");
                            LoginIFrame.this.setVisible(false); // anmeldung erfolgreich login schließen
                            setLoginFieldsToEmpty();
                            loginListener.onLogin(nutzer);
                        }

                    }catch(NumberFormatException e1){
                        JOptionPane.showMessageDialog(LoginIFrame.this, "Bitte Ganzzahlen im Nummernfeld eintagen!");
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
