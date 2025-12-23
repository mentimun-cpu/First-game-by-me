package com.tugasbesar.model.interfaces;

public interface GachaItem {
    // Interface methods
    double getDropRate();
    void onObtain();
    boolean canBeTraded();
    
    // Default method
    default String getGachaAnimation() {
        return "✨ Special Gacha Animation ✨";
    }
}