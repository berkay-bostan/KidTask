package com.kidtask.persistance;

import com.kidtask.model.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskDataService {

    private List<Task> taskList;
    private static final String TASKS_FILE_PATH = "Tasks.txt";

    public TaskDataService() {
        this.taskList = new ArrayList<>();
        loadTasksFromFile();
    }

    private void loadTasksFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(TASKS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String title = parts[0].trim();
                    String description = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    String status = parts[3].trim();

                    // ÖNEMLİ: "Onaylandı" ise listeye ekleme (Yükleme yapma)
                    if (!status.equals("Onaylandı")) {
                        taskList.add(new Task(title, description, points, status));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public void addNewTask(Task task) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASKS_FILE_PATH, true))) {
            String line = String.join(",",
                    task.getTitle(), task.getDescription(),
                    String.valueOf(task.getPoints()), task.getStatus()
            );
            bw.newLine();
            bw.write(line);
            taskList.add(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskStatus(int taskIndex, String newStatus) {
        if (taskIndex >= 0 && taskIndex < taskList.size()) {
            Task task = taskList.get(taskIndex);
            task.setStatus(newStatus);
            saveAllTasksToFile();
        }
    }

    // YENİ METOD: Görevi tamamen siler
    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < taskList.size()) {
            taskList.remove(taskIndex);
            saveAllTasksToFile();
        }
    }

    private void saveAllTasksToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TASKS_FILE_PATH, false))) {
            for (Task task : taskList) {
                String line = String.join(",",
                        task.getTitle(), task.getDescription(),
                        String.valueOf(task.getPoints()), task.getStatus()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}