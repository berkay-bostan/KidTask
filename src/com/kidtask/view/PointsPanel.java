package com.kidtask.view;

import com.kidtask.persistance.PointsDataService; // YENİ İMPORT

import javax.swing.*;
import java.awt.*;

public class PointsPanel extends JPanel {

    private JLabel pointsLabel;
    private JLabel levelLabel;
    private JProgressBar levelProgressBar;

    // GÜNCELLENDİ: Constructor artık PointsDataService alıyor
    public PointsPanel(PointsDataService pointsDataService) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // --- Turuncu Tema ---
        setBackground(new Color(255, 245, 220));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(200, 0));
        // --------------------

        // 1. Başlık
        JLabel titleLabel = new JLabel("Puan Durumu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(230, 130, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        add(javax.swing.Box.createRigidArea(new Dimension(0, 15)));

        // 2. Toplam Puan (GÜNCELLENDİ)
        pointsLabel = new JLabel("Toplam Puan: Yükleniyor..."); // Geçici
        pointsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(pointsLabel);

        add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));

        // 3. Seviye Etiketi (GÜNCELLENDİ)
        levelLabel = new JLabel("Seviye: 1"); // Şimdilik Seviye 1'de kalsın
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(levelLabel);

        // 4. Seviye İlerleme Çubuğu (JProgressBar) (GÜNCELLENDİ)
        levelProgressBar = new JProgressBar(0, 100); // Seviye 1 hedefi (0-100 Puan)
        levelProgressBar.setStringPainted(true);
        levelProgressBar.setForeground(new Color(255, 165, 0));
        levelProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(levelProgressBar);

        // 5. YENİ: Puanları servisten yükle
        updatePointsDisplay(pointsDataService.getCurrentPoints());
    }

    /**
     * (Public Metod) Puan etiketini ve ilerleme çubuğunu günceller.
     * TaskController bu metodu çağıracak.
     */
    public void updatePointsDisplay(int newTotalPoints) {
        pointsLabel.setText("Toplam Puan: " + newTotalPoints);

        // Basit seviye sistemi: Her 100 puanda 1 seviye
        // (Daha sonra geliştirilebilir)
        int currentLevel = 1 + (newTotalPoints / 100);
        int pointsInCurrentLevel = newTotalPoints % 100;

        levelLabel.setText("Seviye: " + currentLevel);
        levelProgressBar.setValue(pointsInCurrentLevel); // 0-100 arası
    }
}