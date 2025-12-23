package com.tugasbesar.service;

import java.util.Random;

import com.tugasbesar.model.Character;
import com.tugasbesar.model.Equipment;
import com.tugasbesar.model.GachaResult;
import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;

public class GachaService {
    private Random random;
    private static final int SINGLE_PULL_COST = 100;
    private static final int MULTI_PULL_COST = 900; // 10% discount
    
    // Constructor
    public GachaService() {
        this.random = new Random();
    }
    
    // Single pull gacha
    public GachaResult singlePull() {
        Rarity rarity = determineRarity();
        GameItem item = generateItem(rarity);
        
        return new GachaResult(item, true, 
            "Single pull successful!", SINGLE_PULL_COST);
    }
    
    // Multi pull (10x) with guaranteed rare+
    public GachaResult[] multiPull() {
        GachaResult[] results = new GachaResult[10];
        boolean hasRareOrAbove = false;
        
        for (int i = 0; i < 10; i++) {
            Rarity rarity;
            if (i == 9 && !hasRareOrAbove) {
                rarity = getGuaranteedRare();
            } else {
                rarity = determineRarity();
            }
            
            if (rarity != Rarity.COMMON) hasRareOrAbove = true;
            
            GameItem item = generateItem(rarity);
            results[i] = new GachaResult(item, true, 
                "Pull #" + (i+1) + " successful!", MULTI_PULL_COST / 10);
        }
        
        return results;
    }
    
    private Rarity determineRarity() {
        int roll = random.nextInt(100) + 1;
        
        if (roll <= Rarity.LEGENDARY.getDropRate()) {
            return Rarity.LEGENDARY;
        } else if (roll <= Rarity.LEGENDARY.getDropRate() + Rarity.EPIC.getDropRate()) {
            return Rarity.EPIC;
        } else if (roll <= Rarity.LEGENDARY.getDropRate() + 
                  Rarity.EPIC.getDropRate() + Rarity.RARE.getDropRate()) {
            return Rarity.RARE;
        } else {
            return Rarity.COMMON;
        }
    }
    
    private Rarity getGuaranteedRare() {
        int roll = random.nextInt(100) + 1;
        if (roll <= 20) return Rarity.LEGENDARY;
        else if (roll <= 50) return Rarity.EPIC;
        else return Rarity.RARE;
    }
    
    private GameItem generateItem(Rarity rarity) {
        String id = "ITEM_" + System.currentTimeMillis() + "_" + random.nextInt(1000);
        
        // 50% chance for Character, 50% for Equipment
        if (random.nextBoolean()) {
            return generateCharacter(id, rarity);
        } else {
            return generateEquipment(id, rarity);
        }
    }
    
    private Character generateCharacter(String id, Rarity rarity) {
        String[] names = {"Warrior", "Mage", "Archer", "Knight", "Assassin", "Priest"};
        String[] elements = {"Fire", "Water", "Earth", "Wind", "Light", "Dark"};
        String name = names[random.nextInt(names.length)] + " of " + elements[random.nextInt(elements.length)];
        
        int baseStat = rarity.getBasePrice() / 10;
        int attack = baseStat + random.nextInt(50);
        int defense = baseStat + random.nextInt(30);
        
        return new Character(id, name, rarity, 
            "A powerful " + rarity.name().toLowerCase() + " character",
            elements[random.nextInt(elements.length)], attack, defense);
    }
    
    private Equipment generateEquipment(String id, Rarity rarity) {
        String[] types = {"Sword", "Armor", "Shield", "Ring", "Amulet", "Boots"};
        String[] prefixes = {"Ancient", "Mystic", "Divine", "Cursed", "Blessed", "Enchanted"};
        
        String type = types[random.nextInt(types.length)];
        String prefix = prefixes[random.nextInt(prefixes.length)];
        String name = prefix + " " + type;
        
        int baseStat = rarity.getBasePrice() / 20;
        int attackBonus = type.equals("Sword") ? baseStat * 2 : baseStat;
        int defenseBonus = type.equals("Armor") || type.equals("Shield") ? baseStat * 2 : baseStat;
        
        return new Equipment(id, name, rarity,
            "A " + rarity.name().toLowerCase() + " " + type + " with special powers",
            type, attackBonus, defenseBonus);
    }
    
    // Getter untuk cost
    public int getSinglePullCost() { return SINGLE_PULL_COST; }
    public int getMultiPullCost() { return MULTI_PULL_COST; }
}