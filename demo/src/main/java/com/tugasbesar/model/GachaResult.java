package com.tugasbesar.model;

import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;

public class GachaResult {
    private GameItem item;
    private boolean isSuccess;
    private String message;
    private int cost;
    
    // Constructor
    public GachaResult(GameItem item, boolean isSuccess, String message, int cost) {
        this.item = item;
        this.isSuccess = isSuccess;
        this.message = message;
        this.cost = cost;
    }
    
    // Getter - enkapsulasi
    public GameItem getItem() { return item; }
    public boolean isSuccess() { return isSuccess; }
    public String getMessage() { return message; }
    public int getCost() { return cost; }
    public Rarity getRarity() { return item != null ? item.getRarity() : null; }
    
    // Methods
    public String getResultSummary() {
        if (isSuccess && item != null) {
            return "🎊 " + message + "\nObtained: " + item.getName() + 
                   " [" + item.getRarity() + "]";
        }
        return "❌ " + message;
    }
}