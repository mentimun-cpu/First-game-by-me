package com.tugasbesar.view;

import com.tugasbesar.controller.GachaController;
import com.tugasbesar.controller.InventoryController;
import com.tugasbesar.model.Player;
import com.tugasbesar.service.UserManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainView {
    private BorderPane root;
    private Player player;
    private GachaController gachaController;
    private InventoryController inventoryController;
    private UserManager userManager;
    private Runnable onLogoutCallback; // Callback untuk logout
    
    private Label playerInfoLabel;
    private Label userInfoLabel;
    private TabPane tabPane;
    
    public MainView(Player player, GachaController gachaController, 
                    InventoryController inventoryController, UserManager userManager,
                    Runnable onLogoutCallback) { // Tambahkan callback parameter
        this.player = player;
        this.gachaController = gachaController;
        this.inventoryController = inventoryController;
        this.userManager = userManager;
        this.onLogoutCallback = onLogoutCallback; // Simpan callback
        
        initializeUI();
    }
    
    private void initializeUI() {
        root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // Center - TabPane (now with Character Management tab)
        tabPane = new TabPane();
        
        // Gacha Tab
        Tab gachaTab = new Tab("🎮 Gacha", new GachaView(gachaController).getRoot());
        gachaTab.setClosable(false);
        
        // Inventory Tab
        Tab inventoryTab = new Tab("📦 Inventory", new InventoryView(inventoryController).getRoot());
        inventoryTab.setClosable(false);
        
        // Character Management Tab (NEW)
        Tab characterTab = new Tab("⚔️ Characters", new CharacterManagementView(player).getRoot());
        characterTab.setClosable(false);
        
        // Player Tab
        Tab playerTab = new Tab("👤 Player", new PlayerView(player).getRoot());
        playerTab.setClosable(false);
        
        tabPane.getTabs().addAll(gachaTab, inventoryTab, characterTab, playerTab);
        root.setCenter(tabPane);
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: linear-gradient(to right, #4A00E0, #8E2DE2); " +
                       "-fx-background-radius: 10; -fx-border-radius: 10;");
        
        Label title = new Label("✨ Fantasy Gacha Game ✨");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        userInfoLabel = new Label("User: " + userManager.getCurrentUser().getUsername());
        userInfoLabel.setFont(new Font("Arial", 12));
        userInfoLabel.setStyle("-fx-text-fill: #FFD700;");
        
        playerInfoLabel = new Label(player.getPlayerInfo());
        playerInfoLabel.setFont(new Font("Arial", 14));
        playerInfoLabel.setStyle("-fx-text-fill: white;");
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> updatePlayerInfo());
        
        Button logoutBtn = new Button("🚪 Logout");
        logoutBtn.setOnAction(e -> logout());
        
        Button exitBtn = new Button("❌ Exit");
        exitBtn.setOnAction(e -> System.exit(0));
        
        buttonBox.getChildren().addAll(refreshBtn, logoutBtn, exitBtn);
        
        header.getChildren().addAll(title, userInfoLabel, playerInfoLabel, buttonBox);
        header.setAlignment(Pos.CENTER);
        
        return header;
    }
    
    private void updatePlayerInfo() {
        playerInfoLabel.setText(player.getPlayerInfo());
    }
    
    private void logout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Logout");
        confirm.setHeaderText("Are you sure you want to logout?");
        confirm.setContentText("Your progress will be saved automatically.");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userManager.logout();
                // Panggil callback untuk logout
                if (onLogoutCallback != null) {
                    onLogoutCallback.run();
                }
            }
        });
    }
    
    public BorderPane getRoot() {
        return root;
    }
}