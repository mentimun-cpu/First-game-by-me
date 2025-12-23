package com.tugasbesar.model.abstracts;

import com.tugasbesar.model.enums.Rarity;

public abstract class GameItem {
    private String id;
    private String name;
    private Rarity rarity;
    private String description;
    
    // Constructor 
    public GameItem(String id, String name, Rarity rarity, String description) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.description = description;
    }
    
    // Abstract method 
    public abstract void displayInfo();
    
    // Encapsulation - Getter dan Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public Rarity getRarity() { return rarity; }
    public String getDescription() { return description; }
    
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRarity(Rarity rarity) { this.rarity = rarity; }
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    // Methods 
    public String getFullInfo() {
        return name + " [" + rarity + "] - " + description;
    }
    
    public boolean isRare() {
        return rarity != Rarity.COMMON;
    }
    
    public String getRarityColor() {
        switch(rarity) {
            case COMMON: return "Gray";
            case RARE: return "Blue";
            case EPIC: return "Purple";
            case LEGENDARY: return "Gold";
            default: return "White";
        }
    }
}