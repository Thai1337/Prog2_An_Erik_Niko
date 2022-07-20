package eshop.ui.gui.iframe;

import eshop.domain.exceptions.AnmeldungFehlgeschlagenException;
import eshop.net.rmi.common.EshopSerializable;
import eshop.ui.gui.StringConverter;
import eshop.valueobjects.Nutzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class LoginIFrame extends JInternalFrame {

    public interface LoginListener {
        public void onLogin(Nutzer nutzer);
    }

    private EshopSerializable shop;

    private LoginListener loginListener;

    private LoginListener loginMenuBarListener;
    private LoginListener loginArtikelMenuBarListener;
    private LoginListener loginMitarbeiterMenuBarListener;
    private JTextField nutzerIDField;

    private JTextField passwortField;

    private JButton loginButton;

    private Nutzer nutzer;

    public LoginIFrame(EshopSerializable shop, LoginListener loginListener, LoginListener loginMenuBarListener, LoginListener loginArtikelMenuBarListener, LoginListener loginMitarbeiterMenuBarListener) {
        super("Anmelden", false, true, false, false);
        this.shop = shop;
        this.loginListener = loginListener;
        this.loginMenuBarListener = loginMenuBarListener;
        this.loginArtikelMenuBarListener = loginArtikelMenuBarListener;
        this.loginMitarbeiterMenuBarListener = loginMitarbeiterMenuBarListener;
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

                    int id = StringConverter.toInteger(nutzerIDField.getText());

                    try{
                        nutzer = shop.mitarbeiterAnmelden(id, pw);
                        if (nutzer == null) {
                            nutzer = shop.kundenAnmelden(id, pw);
                            //((Kunde)nutzer).setMeinWarenkorb(new Warenkorb());
                        }
                        if(nutzer != null) {
                            JOptionPane.showMessageDialog(LoginIFrame.this, "Anmeldung erfolgreich");
                            LoginIFrame.this.setVisible(false); // anmeldung erfolgreich login schließen
                            setLoginFieldsToEmpty();
                            loginListener.onLogin(nutzer);
                            loginMenuBarListener.onLogin(nutzer);
                            loginArtikelMenuBarListener.onLogin(nutzer);
                            loginMitarbeiterMenuBarListener.onLogin(nutzer);
                        }

                    }catch (AnmeldungFehlgeschlagenException e2){
                        JOptionPane.showMessageDialog(LoginIFrame.this, e2.getMessage());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
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
