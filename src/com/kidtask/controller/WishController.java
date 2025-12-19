package com.kidtask.controller;

import com.kidtask.model.Wish;
import com.kidtask.persistance.WishDataService;
import com.kidtask.view.AddWishDialog;
import com.kidtask.view.WishPanel;
import com.kidtask.persistance.PointsDataService;
import com.kidtask.view.PointsPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WishController implements ActionListener {

    private WishPanel view;
    private WishDataService dataService;
    private JFrame mainFrame;
    private PointsDataService pointsService;
    private PointsPanel pointsPanel;

    public WishController(WishPanel view, WishDataService dataService, JFrame mainFrame, PointsDataService pointsService, PointsPanel pointsPanel) {
        this.view = view;
        this.dataService = dataService;
        this.mainFrame = mainFrame;
        this.pointsService = pointsService;
        this.pointsPanel = pointsPanel;

        if (view.getAddWishButton() != null) view.getAddWishButton().addActionListener(this);
        if (view.getApproveWishButton() != null) view.getApproveWishButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == view.getAddWishButton()) handleAddNewWish();
        else if (source == view.getApproveWishButton()) handleApproveWish();
    }

    private void handleAddNewWish() {
        AddWishDialog dialog = new AddWishDialog(mainFrame);
        dialog.setVisible(true);
        Wish newWish = dialog.getNewWish();
        if (newWish != null) {
            dataService.addNewWish(newWish);
            view.addWishToTable(newWish);
        }
    }

    private void handleApproveWish() {
        int selectedRow = view.getWishTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Onaylanacak dileği seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = (String) view.getWishTable().getValueAt(selectedRow, 2);
        int requiredPoints = (Integer) view.getWishTable().getValueAt(selectedRow, 1);

        if (currentStatus.equals("Bekliyor")) {
            // Puan Kontrolü
            boolean success = pointsService.subtractPoints(requiredPoints);

            if (success) {
                // 1. Dileği SİL (Dosya ve Listeden)
                dataService.deleteWish(selectedRow);

                // 2. Ekrandan SİL
                view.removeWishFromTable(selectedRow);

                // 3. Puanı Güncelle
                pointsPanel.updatePointsDisplay(pointsService.getCurrentPoints());

                JOptionPane.showMessageDialog(mainFrame, "Dilek onaylandı! Puan düştü ve dilek temizlendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Yetersiz Puan!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Zaten onaylıysa (normalde listede olmamalı ama güvenlik için)
            JOptionPane.showMessageDialog(mainFrame, "Zaten onaylanmış.", "Bilgi", JOptionPane.WARNING_MESSAGE);
        }
    }
}