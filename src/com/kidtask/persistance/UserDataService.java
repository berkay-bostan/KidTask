package com.kidtask.persistance;

import com.kidtask.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; // Dosya okuma hataları için
import java.util.HashMap;
import java.util.Map;

public class UserDataService {

    private Map<String, User> userMap;
    private static final String USERS_FILE_PATH = "Users.txt"; // Okuyacağımız dosya

    public UserDataService() {
        this.userMap = new HashMap<>();

        // loadTemporaryUsers(); // Artık sahte verileri YÜKLEMİYORUZ

        loadUsersFromFile(); // YENİ: Dosyadan gerçek verileri yüklüyoruz
    }

    private void loadUsersFromFile() {
        // 'try-with-resources' bloğu, dosya okuyucu (br)
        // işi bitince otomatik olarak kapatılır.
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE_PATH))) {

            String line;
            // Dosyada okunacak satır kalmayana kadar (null) döngü devam eder
            while ((line = br.readLine()) != null) {
                // Satırı virgüllere göre parçala
                // Örn: "berkay,123,Parent" -> ["berkay", "123", "Parent"]
                String[] parts = line.split(",");

                // Parçaların doğru sayıda olduğundan emin olalım (3 parça)
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();

                    // Yeni User nesnesi oluştur ve HashMap'e ekle
                    userMap.put(username, new User(username, password, role));
                }
            }
        } catch (IOException e) {
            // Dosya bulunamazsa veya okuma hatası olursa
            // (Şimdilik hatayı konsola yazdıralım)
            e.printStackTrace();
            System.err.println("HATA: Users.txt dosyası okunamadı. " +
                    "Lütfen dosyanın proje ana dizininde olduğundan emin olun.");
        }
    }

    // Bu metodda (validateUser) HİÇBİR DEĞİŞİKLİK YOK.
    // O hala sadece userMap'i kontrol ediyor, verinin nereden
    // geldiğini (dosyadan mı, sahte mi) bilmiyor ve ilgilenmiyor.
    public User validateUser(String username, String password, String role) {
        User user = userMap.get(username);

        if (user != null &&
                user.getPassword().equals(password) &&
                user.getRole().equals(role))
        {
            return user;
        }

        return null;
    }
}