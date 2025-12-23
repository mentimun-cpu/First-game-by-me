package com.tugasbesar.view;

import com.tugasbesar.model.Character;
import com.tugasbesar.model.Equipment;
import com.tugasbesar.model.Player;
import com.tugasbesar.model.enums.Rarity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PlayerView {
    private VBox root;
    private Player player;
    
    public PlayerView(Player player) {
        this.player = player;
        initializeUI();
    }
    
    private void initializeUI() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Player Card
        VBox playerCard = createPlayerCard();
        
        // Statistics
        VBox statsBox = createStatisticsBox();
        
        // Rarity Distribution
        VBox rarityBox = createRarityDistribution();
        
        root.getChildren().addAll(playerCard, statsBox, rarityBox);
    }
    
    private VBox createPlayerCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
                     "-fx-background-radius: 15; -fx-border-radius: 15; " +
                     "-fx-border-width: 2; -fx-border-color: white;");
        card.setPrefWidth(400);
        
        Label title = new Label("👤 PLAYER PROFILE");
        title.setFont(new Font("Arial", 22));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label name = new Label("Name: " + player.getName());
        name.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        
        Label money = new Label("💰 Money: $" + player.getMoney());
        money.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18; -fx-font-weight: bold;");
        
        Label inventory = new Label("📦 Total Items: " + player.getInventorySize());
        inventory.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        
        Label value = new Label("💎 Inventory Value: $" + player.getTotalInventoryValue());
        value.setStyle("-fx-text-fill: #00FF00; -fx-font-size: 14;");
        
        card.getChildren().addAll(title, name, money, inventory, value);
        card.setAlignment(Pos.CENTER_LEFT);
        
        return card;
    }
    
    private VBox createStatisticsBox() {
        VBox statsBox = new VBox(10);
        statsBox.setPadding(new Insets(15));
        statsBox.setStyle("-fx-background-color: #2C3E50; -fx-background-radius: 10;");
        statsBox.setPrefWidth(400);
        
        Label title = new Label("📊 GAME STATISTICS");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        
        // Calculate stats
        int characterCount = player.getCharacters().size();
        int equipmentCount = player.getEquipments().size();
        double avgValue = player.getInventorySize() > 0 ? 
            (double) player.getTotalInventoryValue() / player.getInventorySize() : 0;
        
        // Character stats
        int totalCharPower = player.getCharacters().stream()
            .mapToInt(Character::getPower)
            .sum();
        
        // Equipment stats
        int totalEqBonus = player.getEquipments().stream()
            .mapToInt(Equipment::getTotalBonus)
            .sum();
        
        Label charStats = new Label(String.format(
            "👥 Characters: %d | Total Power: %d | Avg Power: %.1f",
            characterCount, totalCharPower,
            characterCount > 0 ? (double) totalCharPower / characterCount : 0
        ));
        charStats.setStyle("-fx-text-fill: #AED6F1;");
        
        Label eqStats = new Label(String.format(
            "⚔️ Equipment: %d | Total Bonus: %d | Avg Bonus: %.1f",
            equipmentCount, totalEqBonus,
            equipmentCount > 0 ? (double) totalEqBonus / equipmentCount : 0
        ));
        eqStats.setStyle("-fx-text-fill: #F1948A;");
        
        Label valueStats = new Label(String.format(
            "💎 Average Item Value: $%.2f",
            avgValue
        ));
        valueStats.setStyle("-fx-text-fill: #ABEBC6;");
        
        statsBox.getChildren().addAll(title, charStats, eqStats, valueStats);
        
        return statsBox;
    }
    
    private VBox createRarityDistribution() {
        VBox rarityBox = new VBox(10);
        rarityBox.setPadding(new Insets(15));
        rarityBox.setStyle("-fx-background-color: #34495E; -fx-background-radius: 10;");
        rarityBox.setPrefWidth(400);
        
        Label title = new Label("🎨 RARITY DISTRIBUTION");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        
        // Count items by rarity
        int[] counts = new int[Rarity.values().length];
        String[] rarityNames = new String[Rarity.values().length];
        Color[] colors = {Color.GRAY, Color.BLUE, Color.PURPLE, Color.GOLD};
        
        int i = 0;
        for (Rarity rarity : Rarity.values()) {
            rarityNames[i] = rarity.name();
            counts[i] = (int) player.getAllItems().stream()
                .filter(item -> item.getRarity() == rarity)
                .count();
            i++;
        }
        
        // Create progress bars for each rarity
        VBox barsBox = new VBox(5);
        for (int j = 0; j < Rarity.values().length; j++) {
            HBox barBox = new HBox(10);
            barBox.setAlignment(Pos.CENTER_LEFT);
            
            Label rarityLabel = new Label(rarityNames[j]);
            rarityLabel.setPrefWidth(100);
            rarityLabel.setTextFill(colors[j]);
            
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(200);
            double progress = player.getInventorySize() > 0 ? 
                (double) counts[j] / player.getInventorySize() : 0;
            progressBar.setProgress(progress);
            progressBar.setStyle(String.format(
                "-fx-accent: %s;",
                colors[j].toString().replace("0x", "#")
            ));
            
            Label countLabel = new Label(counts[j] + " items");
            countLabel.setTextFill(Color.WHITE);
            
            barBox.getChildren().addAll(rarityLabel, progressBar, countLabel);
            barsBox.getChildren().add(barBox);
        }
        
        rarityBox.getChildren().addAll(title, barsBox);
        
        return rarityBox;
    }
    
    public VBox getRoot() {
        return root;
    }
}