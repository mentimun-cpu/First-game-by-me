package com.tugasbesar.model.enums;

public enum Rarity {
    COMMON(60, "⚪"),
    RARE(25, "🔵"),
    EPIC(10, "🟣"),
    LEGENDARY(5, "🟡");
    
    private final int dropRate;
    private final String symbol;
    
    Rarity(int dropRate, String symbol) {
        this.dropRate = dropRate;
        this.symbol = symbol;
    }
    
    public int getDropRate() { return dropRate; }
    public String getSymbol() { return symbol; }
    
    public int getBasePrice() {
        switch(this) {
            case COMMON: return 50;
            case RARE: return 200;
            case EPIC: return 800;
            case LEGENDARY: return 3000;
            default: return 0;
        }
    }
}