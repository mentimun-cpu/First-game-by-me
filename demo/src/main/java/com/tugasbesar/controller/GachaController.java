package com.tugasbesar.controller;

import com.tugasbesar.model.GachaResult;
import com.tugasbesar.model.Player;
import com.tugasbesar.service.GachaService;

public class GachaController {
    private Player player;
    private GachaService gachaService;
    
    // Constructor
    public GachaController(Player player, GachaService gachaService) {
        this.player = player;
        this.gachaService = gachaService;
    }
    
    // Perform single pull
    public GachaResult performSinglePull() {
        int cost = gachaService.getSinglePullCost();
        
        if (!player.spendMoney(cost)) {
            return new GachaResult(null, false, 
                "Not enough money! Need: $" + cost, cost);
        }
        
        GachaResult result = gachaService.singlePull();
        if (result.isSuccess() && result.getItem() != null) {
            player.addItem(result.getItem());
        }
        
        return result;
    }
    
    // Perform multi pull
    public GachaResult[] performMultiPull() {
        int cost = gachaService.getMultiPullCost();
        
        if (!player.spendMoney(cost)) {
            // Return array with one failed result
            GachaResult[] failed = new GachaResult[1];
            failed[0] = new GachaResult(null, false, 
                "Not enough money! Need: $" + cost, cost);
            return failed;
        }
        
        GachaResult[] results = gachaService.multiPull();
        for (GachaResult result : results) {
            if (result.isSuccess() && result.getItem() != null) {
                player.addItem(result.getItem());
            }
        }
        
        return results;
    }
    
    // Get costs
    public int getSinglePullCost() {
        return gachaService.getSinglePullCost();
    }
    
    public int getMultiPullCost() {
        return gachaService.getMultiPullCost();
    }
    
    // Get player info
    public String getPlayerInfo() {
        return player.getPlayerInfo();
    }
    
    public int getPlayerMoney() {
        return player.getMoney();
    }
}