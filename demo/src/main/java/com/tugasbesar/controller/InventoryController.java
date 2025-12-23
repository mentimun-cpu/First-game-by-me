package com.tugasbesar.controller;

import com.tugasbesar.model.Character;
import com.tugasbesar.model.Equipment;
import com.tugasbesar.model.Player;
import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;

import java.util.List;

public class InventoryController {
    private Player player;
    
    // Constructor
    public InventoryController(Player player) {
        this.player = player;
    }
    
    // === CRUD Operations ===
    
    // CREATE
    public boolean addItem(GameItem item) {
        if (item == null) return false;
        player.addItem(item);
        return true;
    }
    
    // READ
    public List<GameItem> getAllItems() {
        return player.getAllItems();
    }
    
    public List<Character> getCharacters() {
        return player.getCharacters();
    }
    
    public List<Equipment> getEquipments() {
        return player.getEquipments();
    }
    
    public GameItem getItemById(String id) {
        return player.getItemById(id);
    }
    
    // UPDATE
    public boolean updateItem(String id, GameItem newItem) {
        return player.updateItem(id, newItem);
    }
    
    // DELETE
    public boolean deleteItem(String id) {
        return player.removeItem(id);
    }
    
    public boolean sellItem(String id) {
        return player.sellItem(id);
    }
    
    // Filtering methods
    public List<GameItem> filterByRarity(Rarity rarity) {
        List<GameItem> allItems = player.getAllItems();
        return allItems.stream()
                .filter(item -> item.getRarity() == rarity)
                .toList();
    }
    
    public List<GameItem> searchByName(String keyword) {
        List<GameItem> allItems = player.getAllItems();
        return allItems.stream()
                .filter(item -> item.getName().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .toList();
    }
    
    // Statistics
    public int getTotalItems() {
        return player.getInventorySize();
    }
    
    public int getTotalValue() {
        return player.getTotalInventoryValue();
    }
    
    public String getInventoryStats() {
        int chars = player.getCharacters().size();
        int eqs = player.getEquipments().size();
        return String.format("📊 Inventory Stats: Characters: %d | Equipment: %d | Total Value: $%d", 
                chars, eqs, getTotalValue());
    }
}