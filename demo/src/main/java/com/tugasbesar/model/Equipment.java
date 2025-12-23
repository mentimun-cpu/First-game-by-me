package com.tugasbesar.model;

import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;
import com.tugasbesar.model.interfaces.GachaItem;

public class Equipment extends GameItem implements GachaItem {
    private String type; // Weapon, Armor, Accessory
    private int bonusAttack;
    private int bonusDefense;
    private boolean isEquipped;
    private String equippedToCharacterId; // NEW: Track which character has this equipped
    
    // Constructor
    public Equipment(String id, String name, Rarity rarity, 
                    String description, String type, int bonusAttack, int bonusDefense) {
        super(id, name, rarity, description);
        this.type = type;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.isEquipped = false;
        this.equippedToCharacterId = null; // Initially not equipped to anyone
    }
    
    // Override displayInfo method
    @Override
    public void displayInfo() {
        System.out.println("=== Equipment Info ===");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + type);
        System.out.println("Bonus Attack: " + bonusAttack);
        System.out.println("Bonus Defense: " + bonusDefense);
        System.out.println("Equipped: " + (isEquipped ? "Yes" : "No"));
        System.out.println("Equipped to: " + (equippedToCharacterId != null ? equippedToCharacterId : "None"));
    }
    
    // Implementation of interface methods
    @Override
    public double getDropRate() {
        return getRarity().getDropRate() * 0.8; // Equipment has higher drop rate
    }
    
    @Override
    public void onObtain() {
        System.out.println("🎁 You obtained new equipment: " + getName());
    }
    
    @Override
    public boolean canBeTraded() {
        return !isEquipped; // Cannot trade equipped items
    }
    
    // Encapsulation - Getter dan Setter
    public String getType() { return type; }
    public int getBonusAttack() { return bonusAttack; }
    public int getBonusDefense() { return bonusDefense; }
    public boolean isEquipped() { return isEquipped; }
    public String getEquippedToCharacterId() { return equippedToCharacterId; } // NEW
    
    public void setType(String type) { this.type = type; }
    public void setBonusAttack(int bonusAttack) { this.bonusAttack = bonusAttack; }
    public void setBonusDefense(int bonusDefense) { this.bonusDefense = bonusDefense; }
    
    public void setEquipped(boolean equipped) { 
        isEquipped = equipped;
        if (!equipped) {
            equippedToCharacterId = null; // Clear equipped character when unequipped
        }
    }
    
    // NEW: Method to equip to specific character
    public void equipToCharacter(String characterId) {
        isEquipped = true;
        equippedToCharacterId = characterId;
    }
    
    // NEW: Method to unequip
    public void unequip() {
        isEquipped = false;
        equippedToCharacterId = null;
    }
    
    // Additional methods
    public void upgrade() {
        bonusAttack += 10;
        bonusDefense += 5;
    }
    
    public int getTotalBonus() {
        return bonusAttack + bonusDefense;
    }
    
    public String getEquipmentStatus() {
        return isEquipped ? "✅ Equipped" : "❌ Not Equipped";
    }
}