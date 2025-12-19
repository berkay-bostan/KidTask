package com.kidtask.view;

import com.kidtask.model.Task;
import com.kidtask.persistance.TaskDataService;
import com.kidtask.controller.TaskController;
import com.kidtask.persistance.PointsDataService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class TaskPanel extends JPanel {

    private JTable taskTable;
    private JLabel titleLabel;
    private JButton addTaskButton, completeTaskButton, approveTaskButton;
    private TaskDataService taskDataService;
    private DefaultTableModel tableModel;

    public TaskPanel(String userRole, JFrame mainFrame, PointsDataService pointsDataService, PointsPanel pointsPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 245, 255));
        setOpaque(true);

        this.taskDataService = new TaskDataService();

        titleLabel = new JLabel("Görev Yönetim Paneli");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Görev Adı", "Puan", "Durum"};
        // Tabloyu düzenlenemez yapıyoruz
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        taskTable = new JTable(tableModel);
        loadTaskData();
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(210, 230, 255));

        if (userRole.equals("Parent") || userRole.equals("Teacher")) {
            addTaskButton = new JButton("Yeni Görev Ekle");
            approveTaskButton = new JButton("Tamamlanan Görevi Onayla");
            buttonPanel.add(addTaskButton);
            buttonPanel.add(approveTaskButton);
        } else if (userRole.equals("Child")) {
            completeTaskButton = new JButton("Seçili Görevi Tamamla");
            buttonPanel.add(completeTaskButton);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        new TaskController(this, taskDataService, mainFrame, pointsDataService, pointsPanel);
    }

    private void loadTaskData() {
        List<Task> tasks = taskDataService.getAllTasks();
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            Object[] row = { task.getTitle(), task.getPoints(), task.getStatus() };
            tableModel.addRow(row);
        }
    }

    public void addTaskToTable(Task task) {
        tableModel.addRow(new Object[]{ task.getTitle(), task.getPoints(), task.getStatus() });
    }

    public void updateTaskInTable(int rowIndex, String newStatus) {
        tableModel.setValueAt(newStatus, rowIndex, 2);
        tableModel.fireTableRowsUpdated(rowIndex, rowIndex);
    }

    // YENİ METOD: Satırı tamamen siler
    public void removeTaskFromTable(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public JButton getAddTaskButton() { return addTaskButton; }
    public JButton getCompleteTaskButton() { return completeTaskButton; }
    public JButton getApproveTaskButton() { return approveTaskButton; }
    public JTable getTaskTable() { return taskTable; }
}