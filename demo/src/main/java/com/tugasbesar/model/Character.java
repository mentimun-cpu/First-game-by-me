package com.tugasbesar.model;

import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;
import com.tugasbesar.model.interfaces.GachaItem;

public class Character extends GameItem implements GachaItem {
    private String element;
    private int level;
    private int attack;
    private int defense;
    
    // Constructor
    public Character(String id, String name, Rarity rarity, 
                    String description, String element, int attack, int defense) {
        super(id, name, rarity, description);
        this.element = element;
        this.level = 1;
        this.attack = attack;
        this.defense = defense;
    }
    
    // Override displayInfo method
    @Override
    public void displayInfo() {
        System.out.println("=== Character Info ===");
        System.out.println("Name: " + getName());
        System.out.println("Element: " + element);
        System.out.println("Level: " + level);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Rarity: " + getRarity());
    }
    
    // Implementation of interface methods
    @Override
    public double getDropRate() {
        return getRarity().getDropRate() * 0.5; // Characters have lower drop rate
    }
    
    @Override
    public void onObtain() {
        System.out.println("🎉 You obtained a new character: " + getName());
    }
    
    @Override
    public boolean canBeTraded() {
        return getRarity() != Rarity.LEGENDARY;
    }
    
    // Overloading
    public void levelUp() {
        this.level++;
        this.attack += 10;
        this.defense += 5;
    }
    
    public void levelUp(int amount) {
        this.level += amount;
        this.attack += 10 * amount;
        this.defense += 5 * amount;
    }
    
    // Getter dan Setter - Enkapsulasi
    public String getElement() { return element; }
    public int getLevel() { return level; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    
    public void setElement(String element) { this.element = element; }
    public void setLevel(int level) { this.level = level; }
    public void setAttack(int attack) { this.attack = attack; }
    public void setDefense(int defense) { this.defense = defense; }
    
    // Additional methods
    public int getPower() {
        return (attack + defense) * level;
    }
    
    public void train() {
        attack += 5;
        defense += 3;
    }
}