package com.kidtask.persistance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PointsDataService {

    private int currentPoints;
    private static final String POINTS_FILE_PATH = "Points.txt";

    public PointsDataService() {
        loadPointsFromFile();
    }

    private void loadPointsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(POINTS_FILE_PATH))) {
            String line = br.readLine();
            if (line != null) {
                this.currentPoints = Integer.parseInt(line.trim());
            } else {
                this.currentPoints = 0; // Dosya boşsa
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            this.currentPoints = 0; // Hata varsa
        }
    }

    /**
     * Puanı hafızaya ve dosyaya kaydeder.
     */
    private void savePointsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(POINTS_FILE_PATH, false))) {
            bw.write(String.valueOf(currentPoints));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("HATA: Points.txt dosyasına yazma işlemi başarısız.");
        }
    }

    /**
     * Mevcut puanı döndürür.
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Mevcut puana ekleme yapar ve dosyayı günceller.
     * @param pointsToAdd Eklenecek puan
     */
    public void addPoints(int pointsToAdd) {
        this.currentPoints += pointsToAdd;
        savePointsToFile();
    }

   public boolean subtractPoints(int pointsToSubtract){
        if(currentPoints>=pointsToSubtract){
            this.currentPoints-=pointsToSubtract;
            savePointsToFile();
            return true;
        }else{
            return false;
        }
   }
}