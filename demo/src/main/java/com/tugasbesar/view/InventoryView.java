package com.tugasbesar.view;

import com.tugasbesar.controller.InventoryController;
import com.tugasbesar.model.Character;
import com.tugasbesar.model.Equipment;
import com.tugasbesar.model.abstracts.GameItem;
import com.tugasbesar.model.enums.Rarity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InventoryView {
    private VBox root;
    private InventoryController controller;
    private TableView<GameItem> tableView;
    private ObservableList<GameItem> itemList;
    private Label statsLabel;
    
    public InventoryView(InventoryController controller) {
        this.controller = controller;
        initializeUI();
        loadItems();
    }
    
    private void initializeUI() {
        root = new VBox(10);
        root.setPadding(new Insets(15));
        
        // Title and stats
        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        
        Label title = new Label("📦 INVENTORY MANAGEMENT");
        title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");
        
        statsLabel = new Label();
        updateStats();
        
        topBox.getChildren().addAll(title, statsLabel);
        
        // Search and Filter
        HBox filterBox = new HBox(10);
        
        TextField searchField = new TextField();
        searchField.setPromptText("Search items...");
        searchField.setPrefWidth(200);
        
        ComboBox<Rarity> rarityFilter = new ComboBox<>();
        rarityFilter.getItems().addAll(Rarity.values());
        rarityFilter.getItems().add(0, null);
        rarityFilter.setPromptText("Filter by Rarity");
        
        Button searchBtn = new Button("🔍 Search");
        Button clearBtn = new Button("🔄 Clear");
        
        filterBox.getChildren().addAll(
            new Label("Search:"), searchField,
            new Label("Rarity:"), rarityFilter,
            searchBtn, clearBtn
        );
        
        // Table View
        tableView = new TableView<>();
        setupTableColumns();
        itemList = FXCollections.observableArrayList();
        tableView.setItems(itemList);
        
        // CRUD Buttons
        HBox crudButtons = new HBox(10);
        
        Button addBtn = new Button("➕ Add Item");
        Button editBtn = new Button("✏️ Edit");
        Button deleteBtn = new Button("🗑️ Delete");
        Button sellBtn = new Button("💰 Sell");
        Button refreshBtn = new Button("🔄 Refresh");
        
        addBtn.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white;");
        editBtn.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white;");
        deleteBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");
        sellBtn.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
        
        crudButtons.getChildren().addAll(addBtn, editBtn, deleteBtn, sellBtn, refreshBtn);
        
        // Event Handlers
        addBtn.setOnAction(e -> showAddDialog());
        editBtn.setOnAction(e -> showEditDialog());
        deleteBtn.setOnAction(e -> deleteSelectedItem());
        sellBtn.setOnAction(e -> sellSelectedItem());
        refreshBtn.setOnAction(e -> loadItems());
        
        searchBtn.setOnAction(e -> {
            if (!searchField.getText().isEmpty()) {
                searchItems(searchField.getText());
            }
        });
        
        clearBtn.setOnAction(e -> {
            searchField.clear();
            rarityFilter.setValue(null);
            loadItems();
        });
        
        rarityFilter.setOnAction(e -> {
            if (rarityFilter.getValue() != null) {
                filterByRarity(rarityFilter.getValue());
            }
        });
        
        // Add components to root
        root.getChildren().addAll(topBox, filterBox, tableView, crudButtons);
    }
    
    private void setupTableColumns() {
        // ID Column
        TableColumn<GameItem, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));
        idCol.setPrefWidth(100);
        
        // Name Column
        TableColumn<GameItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(150);
        
        // Type Column
        TableColumn<GameItem, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> {
            String type = cellData.getValue() instanceof Character ? "Character" : "Equipment";
            return new javafx.beans.property.SimpleStringProperty(type);
        });
        typeCol.setPrefWidth(80);
        
        // Rarity Column with colors
        TableColumn<GameItem, String> rarityCol = new TableColumn<>("Rarity");
        rarityCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getRarity().name()));
        rarityCol.setCellFactory(column -> new TableCell<GameItem, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    switch(item) {
                        case "COMMON": setTextFill(Color.GRAY); break;
                        case "RARE": setTextFill(Color.BLUE); break;
                        case "EPIC": setTextFill(Color.PURPLE); break;
                        case "LEGENDARY": setTextFill(Color.GOLD); break;
                    }
                }
            }
        });
        rarityCol.setPrefWidth(100);
        
        // Value Column
        TableColumn<GameItem, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                "$" + cellData.getValue().getRarity().getBasePrice()));
        valueCol.setPrefWidth(80);
        
        // Description Column
        TableColumn<GameItem, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDescription()));
        descCol.setPrefWidth(200);
        
        tableView.getColumns().addAll(idCol, nameCol, typeCol, rarityCol, valueCol, descCol);
    }
    
    private void loadItems() {
        itemList.clear();
        itemList.addAll(controller.getAllItems());
        updateStats();
    }
    
    private void searchItems(String keyword) {
        itemList.clear();
        itemList.addAll(controller.searchByName(keyword));
    }
    
    private void filterByRarity(Rarity rarity) {
        itemList.clear();
        itemList.addAll(controller.filterByRarity(rarity));
    }
    
    private void updateStats() {
        statsLabel.setText(controller.getInventoryStats());
    }
    
    private void showAddDialog() {
        // Dialog for adding new item (simplified version)
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Create a new item");
        
        // Create form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Character", "Equipment");
        typeCombo.setValue("Character");
        
        TextField nameField = new TextField();
        nameField.setPromptText("Item Name");
        
        ComboBox<Rarity> rarityCombo = new ComboBox<>();
        rarityCombo.getItems().addAll(Rarity.values());
        rarityCombo.setValue(Rarity.COMMON);
        
        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");
        descArea.setPrefRowCount(3);
        
        grid.add(new Label("Type:"), 0, 0);
        grid.add(typeCombo, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Rarity:"), 0, 2);
        grid.add(rarityCombo, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descArea, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && !nameField.getText().isEmpty()) {
                // Create item based on type
                String id = "CUSTOM_" + System.currentTimeMillis();
                String name = nameField.getText();
                Rarity rarity = rarityCombo.getValue();
                String description = descArea.getText();
                
                GameItem newItem;
                if (typeCombo.getValue().equals("Character")) {
                    newItem = new Character(id, name, rarity, description, 
                                           "Neutral", 50, 30);
                } else {
                    newItem = new Equipment(id, name, rarity, description,
                                           "Custom", 30, 20);
                }
                
                controller.addItem(newItem);
                loadItems();
                
                Alert success = new Alert(Alert.AlertType.INFORMATION,
                    "Item added successfully!", ButtonType.OK);
                success.show();
            }
        });
    }
    
    private void showEditDialog() {
        GameItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an item to edit.");
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit: " + selected.getName());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField(selected.getName());
        TextArea descArea = new TextArea(selected.getDescription());
        descArea.setPrefRowCount(3);
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descArea, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && !nameField.getText().isEmpty()) {
                selected.setName(nameField.getText());
                selected.setDescription(descArea.getText());
                
                // Update in controller
                controller.updateItem(selected.getId(), selected);
                loadItems();
                
                Alert success = new Alert(Alert.AlertType.INFORMATION,
                    "Item updated successfully!", ButtonType.OK);
                success.show();
            }
        });
    }
    
    private void deleteSelectedItem() {
        GameItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an item to delete.");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete: " + selected.getName() + "?",
            ButtonType.YES, ButtonType.NO);
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (controller.deleteItem(selected.getId())) {
                    loadItems();
                    Alert success = new Alert(Alert.AlertType.INFORMATION,
                        "Item deleted successfully!", ButtonType.OK);
                    success.show();
                }
            }
        });
    }
    
    private void sellSelectedItem() {
        GameItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select an item to sell.");
            return;
        }
        
        int sellPrice = selected.getRarity().getBasePrice() / 2;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Sell " + selected.getName() + " for $" + sellPrice + "?",
            ButtonType.YES, ButtonType.NO);
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (controller.sellItem(selected.getId())) {
                    loadItems();
                    Alert success = new Alert(Alert.AlertType.INFORMATION,
                        "Item sold for $" + sellPrice + "!", ButtonType.OK);
                    success.show();
                }
            }
        });
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }
    
    public VBox getRoot() {
        return root;
    }
}