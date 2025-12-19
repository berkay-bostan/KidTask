package com.kidtask.view;

import com.kidtask.model.Wish; // Wish modelini kullanacağız
import javax.swing.*;
import java.awt.*;

public class AddWishDialog extends JDialog {

    private JTextField nameField;
    private JTextField pointsField;
    private JButton saveButton;
    private JButton cancelButton;

    private Wish newWish = null; // Kaydedilen dileği tutmak için

    public AddWishDialog(Frame owner) {
        super(owner, "Yeni Dilek Ekle", true); // Modal = true

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Cıvıl Cıvıl Tema (Açık Yeşil) ---
        getContentPane().setBackground(new Color(240, 255, 240));

        // Alanlar
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Dilek Adı:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Gerekli Puan:"), gbc);
        pointsField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        add(pointsField, gbc);

        // Buton Paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(220, 255, 220));
        saveButton = new JButton("Kaydet");
        cancelButton = new JButton("İptal");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // --- Butonlara İşlevsellik ---
        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        pack(); // İçeriğe göre boyutlandır
        setLocationRelativeTo(owner); // Ana pencereye göre ortala
    }

    // "Kaydet"e basıldığında
    private void onSave() {
        try {
            String name = nameField.getText();
            int points = Integer.parseInt(pointsField.getText());
            String status = "Bekliyor"; // Yeni dilekler onaya düşer

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Dilek adı boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.newWish = new Wish(name, points, status);
            setVisible(false); // Diyalogu kapat

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Puan alanı sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // "İptal"e basıldığında
    private void onCancel() {
        this.newWish = null;
        setVisible(false); // Diyalogu kapat
    }

    /**
     * Controller'ın, diyalog kapatıldıktan sonra
     * kaydedilen Wish'i alması için bu metod kullanılır.
     */
    public Wish getNewWish() {
        return newWish;
    }
}