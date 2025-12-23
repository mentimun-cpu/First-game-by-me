package com.tugasbesar.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.tugasbesar.model.User;

public class UserManager {
    private Map<String, User> users;
    private User currentUser;
    private static final String USER_FILE = "users.dat";
    
    // Constructor
    public UserManager() {
        users = new HashMap<>();
        loadUsers();
        initializeDefaultUsers();
    }
    
    // User operations
    public boolean register(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }
        
        User newUser = new User(username, password, email);
        users.put(username, newUser);
        saveUsers();
        return true;
    }
    
    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.authenticate(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        saveUsers();
        currentUser = null;
    }
    
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.authenticate(oldPassword)) {
                user.setPassword(newPassword);
                saveUsers();
                return true;
            }
        }
        return false;
    }
    
    // Getter
    public User getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
    
    // File operations
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading users: " + e.getMessage());
                users = new HashMap<>();
            }
        }
    }
    
    private void initializeDefaultUsers() {
        // Create default admin user if no users exist
        if (users.isEmpty()) {
            users.put("admin", new User("admin", "admin123", "admin@gacha.com"));
            users.put("player", new User("player", "player123", "player@gacha.com"));
            saveUsers();
        }
    }
}