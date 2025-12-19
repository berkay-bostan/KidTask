package com.kidtask.view;

import com.kidtask.model.Wish;
import com.kidtask.persistance.WishDataService;
import com.kidtask.controller.WishController;
import com.kidtask.persistance.PointsDataService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class WishPanel extends JPanel {

    private JTable wishTable;
    private JLabel titleLabel;
    private JButton addWishButton, approveWishButton;
    private WishDataService wishDataService;
    private DefaultTableModel tableModel;

    public WishPanel(String userRole, JFrame mainFrame, PointsDataService pointsDataService, PointsPanel pointsPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 255, 230));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.wishDataService = new WishDataService();

        titleLabel = new JLabel("Dilek Paneli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 153, 51));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Dilek Adı", "Gerekli Puan", "Durum"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        wishTable = new JTable(tableModel);
        loadWishData();
        JScrollPane scrollPane = new JScrollPane(wishTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(210, 255, 210));

        if (userRole.equals("Parent")) {
            approveWishButton = new JButton("Seçili Dileği Onayla");
            buttonPanel.add(approveWishButton);
        } else if (userRole.equals("Child")) {
            addWishButton = new JButton("Yeni Dilek Ekle");
            buttonPanel.add(addWishButton);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        new WishController(this, wishDataService, mainFrame, pointsDataService, pointsPanel);
    }

    private void loadWishData() {
        List<Wish> wishes = wishDataService.getAllWishes();
        tableModel.setRowCount(0);
        for (Wish wish : wishes) {
            Object[] row = { wish.getName(), wish.getRequiredPoints(), wish.getStatus() };
            tableModel.addRow(row);
        }
    }

    public void addWishToTable(Wish wish) {
        tableModel.addRow(new Object[]{ wish.getName(), wish.getRequiredPoints(), wish.getStatus() });
    }

    public void updateWishInTable(int rowIndex, String newStatus) {
        tableModel.setValueAt(newStatus, rowIndex, 2);
        tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
    }

    // YENİ METOD: Satırı tamamen siler
    public void removeWishFromTable(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public JButton getAddWishButton() { return addWishButton; }
    public JButton getApproveWishButton() { return approveWishButton; }
    public JTable getWishTable() { return wishTable; }
}