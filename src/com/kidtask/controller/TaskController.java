package com.kidtask.controller;

import com.kidtask.model.Task;
import com.kidtask.persistance.TaskDataService;
import com.kidtask.view.AddTaskDialog;
import com.kidtask.view.TaskPanel;
import com.kidtask.persistance.PointsDataService;
import com.kidtask.view.PointsPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskController implements ActionListener {

    private TaskPanel view;
    private TaskDataService dataService;
    private JFrame mainFrame;
    private PointsDataService pointsService;
    private PointsPanel pointsPanel;

    public TaskController(TaskPanel view, TaskDataService dataService, JFrame mainFrame, PointsDataService pointsService, PointsPanel pointsPanel) {
        this.view = view;
        this.dataService = dataService;
        this.mainFrame = mainFrame;
        this.pointsService = pointsService;
        this.pointsPanel = pointsPanel;

        if (view.getAddTaskButton() != null) view.getAddTaskButton().addActionListener(this);
        if (view.getCompleteTaskButton() != null) view.getCompleteTaskButton().addActionListener(this);
        if (view.getApproveTaskButton() != null) view.getApproveTaskButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == view.getAddTaskButton()) handleAddTask();
        else if (source == view.getCompleteTaskButton()) handleCompleteTask();
        else if (source == view.getApproveTaskButton()) handleApproveTask();
    }

    private void handleAddTask() {
        AddTaskDialog dialog = new AddTaskDialog(mainFrame);
        dialog.setVisible(true);
        Task newTask = dialog.getNewTask();
        if (newTask != null) {
            dataService.addNewTask(newTask);
            view.addTaskToTable(newTask);
        }
    }

    private void handleCompleteTask() {
        int selectedRow = view.getTaskTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Lütfen görevi seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = (String) view.getTaskTable().getValueAt(selectedRow, 2);

        if (currentStatus.equals("Bekliyor")) {
            String newStatus = "Tamamlandı";
            dataService.updateTaskStatus(selectedRow, newStatus);
            view.updateTaskInTable(selectedRow, newStatus);
            JOptionPane.showMessageDialog(mainFrame, "Görev tamamlandı! Onay bekleniyor.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } else if (currentStatus.equals("Tamamlandı")) {
            JOptionPane.showMessageDialog(mainFrame, "Zaten tamamlanmış, onay bekleniyor.", "Bilgi", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleApproveTask() {
        int selectedRow = view.getTaskTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Onaylanacak görevi seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = (String) view.getTaskTable().getValueAt(selectedRow, 2);
        int pointsToAdd = (Integer) view.getTaskTable().getValueAt(selectedRow, 1);

        if (currentStatus.equals("Tamamlandı")) {
            // 1. Puan Ver
            pointsService.addPoints(pointsToAdd);
            pointsPanel.updatePointsDisplay(pointsService.getCurrentPoints());

            // 2. Görevi SİL (Dosya ve Listeden)
            dataService.deleteTask(selectedRow);

            // 3. Ekrandan SİL
            view.removeTaskFromTable(selectedRow);

            JOptionPane.showMessageDialog(mainFrame, "Görev onaylandı ve temizlendi! Puan eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

        } else if (currentStatus.equals("Bekliyor")) {
            JOptionPane.showMessageDialog(mainFrame, "Görev henüz tamamlanmamış.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }
}