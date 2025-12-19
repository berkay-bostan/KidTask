package com.kidtask.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton; // Buton için
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane; // Soru sormak için
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout; // Butonu sağa yaslamak için
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Diğer panelleri ve Controller'ları import ediyoruz
import com.kidtask.view.TaskPanel;
import com.kidtask.view.WishPanel;
import com.kidtask.model.User;
import com.kidtask.view.PointsPanel;
import com.kidtask.persistance.PointsDataService;
import com.kidtask.view.LoginScreen;      // YENİ: Giriş ekranına döneceğiz
import com.kidtask.controller.LoginController; // YENİ: Giriş kontrolcüsünü başlatacağız

public class MainDashboard extends JPanel {

    public MainDashboard(User user, JFrame mainFrame) {
        setLayout(new BorderLayout(10, 10));

        setBackground(new Color(255, 250, 240));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        PointsDataService pointsDataService = new PointsDataService();

        // --- 1. ÜST PANEL (HEADER) TASARIMI ---
        // Hoşgeldin yazısı ve Çıkış butonunu yan yana koymak için bir panel yapıyoruz
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false); // Arka planı şeffaf olsun (ana rengi alsın)

        // A) Hoş Geldin Yazısı (Ortada)
        String welcomeText = "Welcome, " + user.getUsername() + "!";
        JLabel welcomeLabel = new JLabel(welcomeText, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(50, 50, 50));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // B) Çıkış Yap Butonu (Sağda)
        JButton logoutButton = new JButton("Log out");
        logoutButton.setBackground(new Color(255, 100, 100)); // Kırmızımsı renk
        logoutButton.setForeground(Color.WHITE); // Yazı rengi beyaz
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));

        // Butonu sağa yaslamak için küçük bir panel daha
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        // Header'ı ana panele ekle
        add(headerPanel, BorderLayout.NORTH);

        // --- ÇIKIŞ BUTONU İŞLEVİ ---
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcıya emin misin diye soralım
                int response = JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you want to log out?",
                        "Log out",
                        JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    // 1. Mevcut Dashboard'u kaldır
                    mainFrame.remove(MainDashboard.this);

                    // 2. Yeni Login Ekranı Oluştur
                    LoginScreen loginScreen = new LoginScreen();

                    // 3. Login Controller'ı Başlat
                    new LoginController(loginScreen, mainFrame);

                    // 4. Login Ekranını Çerçeveye Ekle
                    mainFrame.add(loginScreen);

                    // 5. Ekranı Yenile
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
            }
        });

        // 2. TaskPanel (CENTER)
        // TaskPanel'e PointsPanel'i göndermeliyiz, o yüzden önce PointsPanel'i oluşturuyoruz.

        // 4. PointsPanel (WEST) - Önce oluşturulmalı
        PointsPanel pointsPanel = new PointsPanel(pointsDataService);
        add(pointsPanel, BorderLayout.WEST);

        // TaskPanel oluşturulurken pointsPanel referansı veriliyor
        TaskPanel taskPanel = new TaskPanel(user.getRole(), mainFrame, pointsDataService, pointsPanel);
        add(taskPanel, BorderLayout.CENTER);

        // 3. WishPanel (EAST)
        WishPanel wishPanel = new WishPanel(user.getRole(), mainFrame, pointsDataService, pointsPanel);
        add(wishPanel, BorderLayout.EAST);
    }
}