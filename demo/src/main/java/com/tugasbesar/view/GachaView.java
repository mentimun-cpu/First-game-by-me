package com.tugasbesar.view;

import com.tugasbesar.controller.GachaController;
import com.tugasbesar.model.GachaResult;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GachaView {
    private VBox root;
    private GachaController gachaController;
    private Label resultLabel;
    private TextArea resultArea;
    private Button singlePullBtn;
    private Button multiPullBtn;
    
    public GachaView(GachaController gachaController) {
        this.gachaController = gachaController;
        initializeUI();
    }
    
    private void initializeUI() {
        root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Title
        Label title = new Label("GACHA MACHINE ");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-text-fill: #8B00FF; -fx-font-weight: bold;");
        
        // Player info
        Label moneyLabel = new Label("Current Money: $" + gachaController.getPlayerMoney());
        moneyLabel.setFont(new Font("Arial", 16));
        
        // Gacha buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        
        singlePullBtn = createGachaButton("Single Pull", 
            "Roll once\nCost: $" + gachaController.getSinglePullCost(), 
            Color.LIGHTBLUE);
        
        multiPullBtn = createGachaButton("10x Multi Pull", 
            "Roll 10 times\nCost: $" + gachaController.getMultiPullCost() + 
            "\n(10% discount!)", Color.LIGHTPINK);
        
        singlePullBtn.setOnAction(e -> performSinglePull());
        multiPullBtn.setOnAction(e -> performMultiPull());
        
        buttonBox.getChildren().addAll(singlePullBtn, multiPullBtn);
        
        // Result display
        resultLabel = new Label(" Pull results will appear here!");
        resultLabel.setFont(new Font("Arial", 14));
        
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(150);
        resultArea.setStyle("-fx-font-family: 'Monospace'; -fx-font-size: 12;");
        
        // Instructions
        Label instructions = new Label(
            " Drop Rates: LEGENDARY (5%) | EPIC (10%) | RARE (25%) | COMMON (60%)\n" +
            " Multi Pull guarantees at least one RARE or higher item!"
        );
        instructions.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");
        instructions.setWrapText(true);
        
        root.getChildren().addAll(title, moneyLabel, buttonBox, 
                                 resultLabel, resultArea, instructions);
    }
    
    private Button createGachaButton(String text, String tooltip, Color color) {
        Button button = new Button(text);
        button.setPrefSize(200, 100);
        button.setStyle(String.format(
            "-fx-font-size: 16; -fx-font-weight: bold; " +
            "-fx-background-color: %s; -fx-background-radius: 10; " +
            "-fx-border-radius: 10; -fx-border-width: 2;",
            color.toString().replace("0x", "#")
        ));
        
        Tooltip tip = new Tooltip(tooltip);
        Tooltip.install(button, tip);
        
        return button;
    }
    
    private void performSinglePull() {
        GachaResult result = gachaController.performSinglePull();
        
        if (result.isSuccess()) {
            resultLabel.setText(" Pull Successful!");
            resultArea.setText(result.getResultSummary() + "\n" +
                "Remaining Money: $" + gachaController.getPlayerMoney());
        } else {
            resultLabel.setText(" Pull Failed!");
            resultArea.setText(result.getMessage() + "\n" +
                "Current Money: $" + gachaController.getPlayerMoney());
        }
        
        // Update button state
        updateButtonStates();
    }
    
    private void performMultiPull() {
        GachaResult[] results = gachaController.performMultiPull();
        
        if (results.length > 0 && results[0].isSuccess()) {
            resultLabel.setText("🎊 10x Pull Successful!");
            
            StringBuilder sb = new StringBuilder();
            sb.append("=== MULTI PULL RESULTS ===\n");
            
            int legendary = 0, epic = 0, rare = 0, common = 0;
            
            for (int i = 0; i < results.length; i++) {
                GachaResult result = results[i];
                sb.append(String.format("%02d. ", i+1))
                  .append(result.getResultSummary()).append("\n");
                
                if (result.getRarity() != null) {
                    switch(result.getRarity()) {
                        case LEGENDARY: legendary++; break;
                        case EPIC: epic++; break;
                        case RARE: rare++; break;
                        case COMMON: common++; break;
                    }
                }
            }
            
            sb.append("\n Summary: LEGENDARY(").append(legendary)
              .append(") EPIC(").append(epic)
              .append(") RARE(").append(rare)
              .append(") COMMON(").append(common).append(")\n");
            
            sb.append("💰 Remaining Money: $").append(gachaController.getPlayerMoney());
            
            resultArea.setText(sb.toString());
        } else if (results.length > 0) {
            resultLabel.setText("❌ Multi Pull Failed!");
            resultArea.setText(results[0].getMessage() + "\n" +
                "Current Money: $" + gachaController.getPlayerMoney());
        }
        
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        int money = gachaController.getPlayerMoney();
        singlePullBtn.setDisable(money < gachaController.getSinglePullCost());
        multiPullBtn.setDisable(money < gachaController.getMultiPullCost());
    }
    
    public VBox getRoot() {
        return root;
    }
}