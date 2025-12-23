package com.tugasbesar.model;

public class User {
    private String username;
    private String password;
    private String email;
    private Player player;
    
    // Constructor
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.player = new Player(username, 1000); // Default starting money
    }
    
    // Authentication methods
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public void changePassword(String oldPassword, String newPassword) {
        if (authenticate(oldPassword)) {
            this.password = newPassword;
        }
    }
    
    // Encapsulation - Getter dan Setter
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Player getPlayer() { return player; }
    
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    
    // Additional methods
    public String getUserInfo() {
        return "👤 " + username + " | 📧 " + email + " | 💰 $" + player.getMoney();
    }
}