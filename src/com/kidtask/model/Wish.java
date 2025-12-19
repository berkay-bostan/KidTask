package com.kidtask.model;

public class Wish {
    private String name;
    private int requiredPoints;
    private String status; // Örn: "Bekliyor", "Onaylandı"

    public Wish(String name, int requiredPoints, String status) {
        this.name = name;
        this.requiredPoints = requiredPoints;
        this.status = status;
    }

    // Getter metodları
    public String getName() {
        return name;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
}