package com.tugasbesar.view;

import com.tugasbesar.model.Character;
import com.tugasbesar.model.Equipment;
import com.tugasbesar.model.Player;
import com.tugasbesar.model.abstracts.GameItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CharacterManagementView {
    private VBox root;
    private Player player;
    private ListView<Character> characterListView;
    private ListView<Equipment> equipmentListView;
    private Label selectedCharacterLabel;
    private Label equippedItemsLabel;
    
    public CharacterManagementView(Player player) {
        this.player = player;
        initializeUI();
        loadData();
    }
    
    private void initializeUI() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1a1a2e;");
        
        // Title
        Label title = new Label("⚔️ CHARACTER MANAGEMENT");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.WHITE);
        
        // Main content split into two columns
        HBox mainContent = new HBox(20);
        mainContent.setPadding(new Insets(10));
        
        // Left column - Characters
        VBox charactersBox = new VBox(10);
        charactersBox.setStyle("-fx-background-color: #16213e; -fx-padding: 15; -fx-background-radius: 10;");
        charactersBox.setPrefWidth(350);
        
        Label charactersTitle = new Label("🎭 Your Characters");
        charactersTitle.setFont(new Font("Arial", 16));
        charactersTitle.setTextFill(Color.WHITE);
        
        characterListView = new ListView<>();
        characterListView.setPrefHeight(300);
        characterListView.setCellFactory(param -> new ListCell<Character>() {
            @Override
            protected void updateItem(Character character, boolean empty) {
                super.updateItem(character, empty);
                if (empty || character == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(character.getName() + " | Lvl " + character.getLevel() + 
                           " | ⚔️" + character.getAttack() + " 🛡️" + character.getDefense());
                    setTextFill(getRarityColor(character.getRarity()));
                    setStyle("-fx-font-weight: bold;");
                }
            }
        });
        
        // Right column - Equipment and Details
        VBox detailsBox = new VBox(10);
        detailsBox.setStyle("-fx-background-color: #0f3460; -fx-padding: 15; -fx-background-radius: 10;");
        detailsBox.setPrefWidth(400);
        
        // Selected character info
        VBox characterInfoBox = new VBox(10);
        selectedCharacterLabel = new Label("Select a character to view details");
        selectedCharacterLabel.setTextFill(Color.WHITE);
        selectedCharacterLabel.setWrapText(true);
        
        equippedItemsLabel = new Label("No items equipped");
        equippedItemsLabel.setTextFill(Color.LIGHTGRAY);
        equippedItemsLabel.setWrapText(true);
        
        characterInfoBox.getChildren().addAll(selectedCharacterLabel, equippedItemsLabel);
        
        // Available equipment
        Label equipmentTitle = new Label("🎒 Available Equipment");
        equipmentTitle.setFont(new Font("Arial", 16));
        equipmentTitle.setTextFill(Color.WHITE);
        
        equipmentListView = new ListView<>();
        equipmentListView.setPrefHeight(200);
        equipmentListView.setCellFactory(param -> new ListCell<Equipment>() {
            @Override
            protected void updateItem(Equipment equipment, boolean empty) {
                super.updateItem(equipment, empty);
                if (empty || equipment == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String equipped = equipment.isEquipped() ? "✅ " : "❌ ";
                    setText(equipped + equipment.getName() + 
                           " | ⚔️+" + equipment.getBonusAttack() + 
                           " 🛡️+" + equipment.getBonusDefense());
                    setTextFill(getRarityColor(equipment.getRarity()));
                }
            }
        });
        
        // Action buttons
        HBox actionButtons = new HBox(10);
        Button equipButton = new Button("⚔️ Equip");
        equipButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        equipButton.setPrefWidth(100);
        
        Button unequipButton = new Button("❌ Unequip");
        unequipButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        unequipButton.setPrefWidth(100);
        
        Button levelUpButton = new Button("⬆️ Level Up");
        levelUpButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        levelUpButton.setPrefWidth(100);
        
        actionButtons.getChildren().addAll(equipButton, unequipButton, levelUpButton);
        actionButtons.setAlignment(Pos.CENTER);
        
        // Add to details box
        detailsBox.getChildren().addAll(characterInfoBox, equipmentTitle, equipmentListView, actionButtons);
        
        // Add to characters box
        charactersBox.getChildren().addAll(charactersTitle, characterListView);
        
        // Add to main content
        mainContent.getChildren().addAll(charactersBox, detailsBox);
        
        // Event Handlers
        characterListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    updateCharacterDetails(newValue);
                }
            });
        
        equipButton.setOnAction(e -> equipSelectedItem());
        unequipButton.setOnAction(e -> unequipSelectedItem());
        levelUpButton.setOnAction(e -> levelUpSelectedCharacter());
        
        // Stats summary
        Label statsSummary = new Label();
        statsSummary.setTextFill(Color.WHITE);
        statsSummary.setStyle("-fx-font-size: 12;");
        
        // Add all to root
        root.getChildren().addAll(title, mainContent, statsSummary);
        
        // Initial update
        updateStatsSummary(statsSummary);
    }
    
    private void loadData() {
        ObservableList<Character> characters = FXCollections.observableArrayList(player.getCharacters());
        characterListView.setItems(characters);
        
        ObservableList<Equipment> equipment = FXCollections.observableArrayList(player.getEquipments());
        equipmentListView.setItems(equipment);
    }
    
    private void updateCharacterDetails(Character character) {
        selectedCharacterLabel.setText(
            "🧬 " + character.getName() + "\n" +
            "🎭 Level: " + character.getLevel() + "\n" +
            "⚔️ Attack: " + character.getAttack() + "\n" +
            "🛡️ Defense: " + character.getDefense() + "\n" +
            "💪 Power: " + character.getPower() + "\n" +
            "🔥 Element: " + character.getElement() + "\n" +
            "🌟 Rarity: " + character.getRarity()
        );
        
        // Find equipped items for this character
        StringBuilder equippedItems = new StringBuilder("🎒 Equipped Items:\n");
        boolean hasEquipped = false;
        
        for (Equipment equipment : player.getEquipments()) {
            if (equipment.isEquipped()) {
                equippedItems.append("• ").append(equipment.getName())
                            .append(" (⚔️+").append(equipment.getBonusAttack())
                            .append(" 🛡️+").append(equipment.getBonusDefense())
                            .append(")\n");
                hasEquipped = true;
            }
        }
        
        if (!hasEquipped) {
            equippedItems.append("No items equipped");
        }
        
        equippedItemsLabel.setText(equippedItems.toString());
    }
    
    private void equipSelectedItem() {
        Character selectedCharacter = characterListView.getSelectionModel().getSelectedItem();
        Equipment selectedEquipment = equipmentListView.getSelectionModel().getSelectedItem();
        
        if (selectedCharacter == null || selectedEquipment == null) {
            showAlert("Error", "Please select both a character and an equipment!");
            return;
        }
        
        if (selectedEquipment.isEquipped()) {
            showAlert("Error", "This equipment is already equipped to another character!");
            return;
        }
        
        // Unequip any equipment of same type
        for (Equipment equipment : player.getEquipments()) {
            if (equipment.isEquipped() && equipment.getType().equals(selectedEquipment.getType())) {
                equipment.setEquipped(false);
            }
        }
        
        // Equip the selected equipment
        selectedEquipment.setEquipped(true);
        
        // Update character stats (temporary bonus)
        selectedCharacter.setAttack(selectedCharacter.getAttack() + selectedEquipment.getBonusAttack());
        selectedCharacter.setDefense(selectedCharacter.getDefense() + selectedEquipment.getBonusDefense());
        
        showAlert("Success", selectedEquipment.getName() + " equipped to " + selectedCharacter.getName() + "!");
        
        // Refresh displays
        loadData();
        updateCharacterDetails(selectedCharacter);
    }
    
    private void unequipSelectedItem() {
        Equipment selectedEquipment = equipmentListView.getSelectionModel().getSelectedItem();
        
        if (selectedEquipment == null) {
            showAlert("Error", "Please select an equipment to unequip!");
            return;
        }
        
        if (!selectedEquipment.isEquipped()) {
            showAlert("Error", "This equipment is not equipped!");
            return;
        }
        
        // Find which character has this equipped and remove bonus
        for (Character character : player.getCharacters()) {
            // In a real system, you'd track which character has which equipment
            // For now, we'll just remove from all characters
            character.setAttack(character.getAttack() - selectedEquipment.getBonusAttack());
            character.setDefense(character.getDefense() - selectedEquipment.getBonusDefense());
        }
        
        selectedEquipment.setEquipped(false);
        
        showAlert("Success", selectedEquipment.getName() + " unequipped!");
        
        // Refresh displays
        loadData();
        updateCharacterDetails(characterListView.getSelectionModel().getSelectedItem());
    }
    
    private void levelUpSelectedCharacter() {
        Character selectedCharacter = characterListView.getSelectionModel().getSelectedItem();
        
        if (selectedCharacter == null) {
            showAlert("Error", "Please select a character to level up!");
            return;
        }
        
        // Cost for leveling up based on current level
        int levelUpCost = selectedCharacter.getLevel() * 100;
        
        if (player.getMoney() < levelUpCost) {
            showAlert("Error", "Not enough gold! Need: $" + levelUpCost);
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Level Up");
        confirm.setHeaderText("Level up " + selectedCharacter.getName() + "?");
        confirm.setContentText("Cost: $" + levelUpCost + "\n" +
                              "Current Level: " + selectedCharacter.getLevel() + " → " + (selectedCharacter.getLevel() + 1));
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                player.spendMoney(levelUpCost);
                selectedCharacter.levelUp();
                showAlert("Success", selectedCharacter.getName() + " leveled up to " + selectedCharacter.getLevel() + "!");
                
                // Refresh displays
                loadData();
                updateCharacterDetails(selectedCharacter);
            }
        });
    }
    
    private Color getRarityColor(com.tugasbesar.model.enums.Rarity rarity) {
        switch (rarity) {
            case COMMON: return Color.GRAY;
            case RARE: return Color.BLUE;
            case EPIC: return Color.PURPLE;
            case LEGENDARY: return Color.GOLD;
            default: return Color.WHITE;
        }
    }
    
    private void updateStatsSummary(Label label) {
        int totalCharacters = player.getCharacters().size();
        int totalEquipment = player.getEquipments().size();
        int equippedCount = (int) player.getEquipments().stream().filter(Equipment::isEquipped).count();
        int totalPower = player.getCharacters().stream().mapToInt(Character::getPower).sum();
        
        label.setText(
            "📊 Summary: Characters: " + totalCharacters + 
            " | Equipment: " + totalEquipment + 
            " | Equipped: " + equippedCount + 
            " | Total Power: " + totalPower
        );
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getRoot() {
        return root;
    }
}