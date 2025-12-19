package com.kidtask.model;

public class Task {
    private String title;
    private String description;
    private int points;
    private String status; // Örn: "Bekliyor", "Tamamlandı", "Onaylandı"

    public Task(String title, String description, int points, String status) {
        this.title = title;
        this.description = description;
        this.points = points;
        this.status = status;
    }

    // Bu bilgilere dışarıdan erişmek için getter metodları
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}