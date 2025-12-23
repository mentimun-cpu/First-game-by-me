package com.tugasbesar.model;

import java.util.ArrayList;
import java.util.List;

import com.tugasbesar.model.abstracts.GameItem;

public class Player {
    private String name;
    private int money;
    private List<GameItem> inventory;
    
    // Constructor
    public Player(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
        this.inventory = new ArrayList<>();
        System.out.println("Player " + name + " created with $" + startingMoney);
    }
    
    // NEW: Equip equipment to character
    public boolean equipItem(String characterId, String equipmentId) {
        Character character = null;
        Equipment equipment = null;
        
        // Find character and equipment
        for (GameItem item : inventory) {
            if (item instanceof Character && item.getId().equals(characterId)) {
                character = (Character) item;
            }
            if (item instanceof Equipment && item.getId().equals(equipmentId)) {
                equipment = (Equipment) item;
            }
        }
        
        if (character == null || equipment == null) {
            return false;
        }
        
        // Check if equipment is already equipped
        if (equipment.isEquipped()) {
            return false;
        }
        
        // Unequip any equipment of same type that character might have
        for (GameItem item : inventory) {
            if (item instanceof Equipment) {
                Equipment eq = (Equipment) item;
                if (eq.isEquipped() && eq.getEquippedToCharacterId() != null && 
                    eq.getEquippedToCharacterId().equals(characterId)) {
                    eq.unequip();
                }
            }
        }
        
        // Equip the new equipment
        equipment.equipToCharacter(characterId);
        
        // Apply bonuses to character
        character.setAttack(character.getAttack() + equipment.getBonusAttack());
        character.setDefense(character.getDefense() + equipment.getBonusDefense());
        
        return true;
    }
    
    // NEW: Unequip equipment
    public boolean unequipItem(String equipmentId) {
        Equipment equipment = null;
        
        // Find equipment
        for (GameItem item : inventory) {
            if (item instanceof Equipment && item.getId().equals(equipmentId)) {
                equipment = (Equipment) item;
                break;
            }
        }
        
        if (equipment == null || !equipment.isEquipped()) {
            return false;
        }
        
        // Find character and remove bonuses
        String characterId = equipment.getEquippedToCharacterId();
        if (characterId != null) {
            for (GameItem item : inventory) {
                if (item instanceof Character && item.getId().equals(characterId)) {
                    Character character = (Character) item;
                    character.setAttack(character.getAttack() - equipment.getBonusAttack());
                    character.setDefense(character.getDefense() - equipment.getBonusDefense());
                    break;
                }
            }
        }
        
        // Unequip
        equipment.unequip();
        return true;
    }
    
    // NEW: Get equipped items for a character
    public List<Equipment> getEquippedItemsForCharacter(String characterId) {
        List<Equipment> equippedItems = new ArrayList<>();
        for (GameItem item : inventory) {
            if (item instanceof Equipment) {
                Equipment equipment = (Equipment) item;
                if (equipment.isEquipped() && characterId.equals(equipment.getEquippedToCharacterId())) {
                    equippedItems.add(equipment);
                }
            }
        }
        return equippedItems;
    }
    
    // Add item to inventory
    public void addItem(GameItem item) {
        inventory.add(item);
        System.out.println("Added " + item.getName() + " to inventory");
    }
    
    // READ items or specific item
    public List<GameItem> getAllItems() {
        return new ArrayList<>(inventory);
    }
    
    public GameItem getItemById(String id) {
        for (GameItem item : inventory) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    
    public List<Character> getCharacters() {
        List<Character> characters = new ArrayList<>();
        for (GameItem item : inventory) {
            if (item instanceof Character) {
                characters.add((Character) item);
            }
        }
        return characters;
    }
    
    public List<Equipment> getEquipments() {
        List<Equipment> equipments = new ArrayList<>();
        for (GameItem item : inventory) {
            if (item instanceof Equipment) {
                equipments.add((Equipment) item);
            }
        }
        return equipments;
    }
    
    // Update item in inventory
    public boolean updateItem(String id, GameItem newItem) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equals(id)) {
                inventory.set(i, newItem);
                System.out.println("Updated item: " + newItem.getName());
                return true;
            }
        }
        return false;
    }
    
    // Remove item from inventory
    public boolean removeItem(String id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equals(id)) {
                GameItem removed = inventory.remove(i);
                System.out.println("Removed item: " + removed.getName());
                return true;
            }
        }
        return false;
    }
    
    public boolean sellItem(String id) {
        GameItem item = getItemById(id);
        if (item != null) {
            int sellPrice = item.getRarity().getBasePrice() / 2;
            removeItem(id);
            addMoney(sellPrice);
            System.out.println("Sold " + item.getName() + " for $" + sellPrice);
            return true;
        }
        return false;
    }
    
    // manajemen uang  
    public boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
    
    public void addMoney(int amount) {
        money += amount;
    }
    
    //enkapsulasi - Getter dan Setter
    public String getName() { return name; }
    public int getMoney() { return money; }
    public int getInventorySize() { return inventory.size(); }
    
    public void setName(String name) { this.name = name; }
    public void setMoney(int money) { this.money = money; }
    
    // Additional methods
    public int getTotalInventoryValue() {
        int total = 0;
        for (GameItem item : inventory) {
            total += item.getRarity().getBasePrice();
        }
        return total;
    }
    
    public String getPlayerInfo() {
        return "👤 " + name + " | 💰 $" + money + " | 📦 Items: " + inventory.size();
    }
}