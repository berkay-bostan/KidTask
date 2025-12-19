package com.kidtask.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField; // Kullanıcı adı için
import javax.swing.JPasswordField; // Şifre için
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LoginScreen extends JPanel {

    private JComboBox<String> roleSelector;
    private JButton loginButton;
    private JLabel titleLabel;

    // YENİ BİLEŞENLER
    private JLabel roleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Bileşenler arası boşluk (üst, sol, alt, sağ)
        gbc.anchor = GridBagConstraints.WEST; // Bileşenleri sola yasla

        // 1. Başlık
        titleLabel = new JLabel("Welcome to KidTask");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Başlık 2 sütun kaplasın
        gbc.anchor = GridBagConstraints.CENTER; // Başlığı ortala
        add(titleLabel, gbc);

        // --- Reset gbc ---
        gbc.gridwidth = 1; // Genişliği normale döndür
        gbc.anchor = GridBagConstraints.WEST; // Yaslamayı sola döndür

        // 2. Rol Seçimi
        roleLabel = new JLabel("Rolünüz:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(roleLabel, gbc);

        String[] roles = {"Child", "Parent", "Teacher"};
        roleSelector = new JComboBox<>(roles);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Alanı yatayda doldur
        add(roleSelector, gbc);

        // 3. Kullanıcı Adı
        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE; // Doldurmayı sıfırla
        add(usernameLabel, gbc);

        usernameField = new JTextField(15); // 15 karakter genişliğinde
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        // 4. Şifre
        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // 5. Giriş Butonu
        loginButton = new JButton("Login");
        gbc.gridx = 1; // Sağ sütunda (giriş alanlarının altında)
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST; // Butonu sağa yasla
        add(loginButton, gbc);
    }

    // Controller'ın bu bileşenlere erişebilmesi için "getter" metodları
    public JComboBox<String> getRoleSelector() {
        return roleSelector;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    // YENİ GETTER'LAR
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}