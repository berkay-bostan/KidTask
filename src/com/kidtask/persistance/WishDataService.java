package com.kidtask.persistance;

import com.kidtask.model.Wish;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WishDataService {

    private List<Wish> wishList;
    private static final String WISHES_FILE_PATH = "Wishes.txt";

    public WishDataService() {
        this.wishList = new ArrayList<>();
        loadWishesFromFile();
    }

    private void loadWishesFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(WISHES_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int points = Integer.parseInt(parts[1].trim());
                    String status = parts[2].trim();

                    // ÖNEMLİ: "Onaylandı" ise listeye ekleme
                    if (!status.equals("Onaylandı")) {
                        wishList.add(new Wish(name, points, status));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<Wish> getAllWishes() { return wishList; }

    public void addNewWish(Wish wish) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(WISHES_FILE_PATH, true))) {
            String line = String.join(",", wish.getName(), String.valueOf(wish.getRequiredPoints()), wish.getStatus());
            bw.newLine();
            bw.write(line);
            wishList.add(wish);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void updateWishStatus(int wishIndex, String newStatus) {
        if (wishIndex >= 0 && wishIndex < wishList.size()) {
            Wish wish = wishList.get(wishIndex);
            wish.setStatus(newStatus);
            saveAllWishesToFile();
        }
    }

    // YENİ METOD: Dileği tamamen siler
    public void deleteWish(int index) {
        if (index >= 0 && index < wishList.size()) {
            wishList.remove(index);
            saveAllWishesToFile();
        }
    }

    private void saveAllWishesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(WISHES_FILE_PATH, false))) {
            for (Wish wish : wishList) {
                String line = String.join(",", wish.getName(), String.valueOf(wish.getRequiredPoints()), wish.getStatus());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}