package com.kidtask.view;

import com.kidtask.model.Task; // Task modelini kullanacağız
import javax.swing.*;
import java.awt.*;

public class AddTaskDialog extends JDialog {

    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField pointsField;
    private JButton saveButton;
    private JButton cancelButton;

    private Task newTask = null; // Kaydedilen görevi tutmak için

    public AddTaskDialog(Frame owner) {
        // 'owner' (sahibi olan pencere) ve 'modal' (bu açıkken arkadakine tıklanamaz)
        super(owner, "Yeni Görev Ekle", true);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Cıvıl Cıvıl Tema (Açık Mavi) ---
        getContentPane().setBackground(new Color(240, 248, 255));

        // Alanlar
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Görev Başlığı:"), gbc);
        titleField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Açıklama:"), gbc);
        descriptionField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Puan:"), gbc);
        pointsField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        add(pointsField, gbc);

        // Buton Paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(220, 235, 255));
        saveButton = new JButton("Kaydet");
        cancelButton = new JButton("İptal");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // İki sütunu kapla
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // --- Butonlara İşlevsellik ---

        // "Kaydet" Butonu
        saveButton.addActionListener(e -> onSave());

        // "İptal" Butonu
        cancelButton.addActionListener(e -> onCancel());

        pack(); // İçeriğe göre boyutlandır
        setLocationRelativeTo(owner); // Ana pencereye göre ortala
    }

    // "Kaydet"e basıldığında
    private void onSave() {
        try {
            String title = titleField.getText();
            String description = descriptionField.getText();
            int points = Integer.parseInt(pointsField.getText());
            String status = "Bekliyor"; // Yeni eklenen görevler "Bekliyor" durumundadır

            if (title.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanlar doldurulmalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Yeni Task nesnesini oluştur
            this.newTask = new Task(title, description, points, status);
            setVisible(false); // Diyalogu kapat

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Puan alanı sayı olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // "İptal"e basıldığında
    private void onCancel() {
        this.newTask = null; // Hiçbir şey yapma
        setVisible(false); // Diyalogu kapat
    }

    /**
     * Controller'ın, diyalog kapatıldıktan sonra
     * kaydedilen Task'i alması için bu metod kullanılır.
     */
    public Task getNewTask() {
        return newTask;
    }
}