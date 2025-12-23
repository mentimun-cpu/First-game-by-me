package com.tugasbesar;

import com.tugasbesar.controller.GachaController;
import com.tugasbesar.controller.InventoryController;
import com.tugasbesar.model.Player;
import com.tugasbesar.service.GachaService;
import com.tugasbesar.service.UserManager;
import com.tugasbesar.view.LoginView;
import com.tugasbesar.view.MainView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private UserManager userManager;
    private GachaService gachaService;
    private GachaController gachaController;
    private InventoryController inventoryController;
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.userManager = new UserManager();
        
        // Show login screen
        showLoginScreen();
    }
    
    private void showLoginScreen() {
        LoginView loginView = new LoginView(primaryStage, userManager, this::showMainGame);
        loginView.show();
    }
    
    private void showMainGame() {
        Player player = userManager.getCurrentUser().getPlayer();
        
        // Initialize game services
        this.gachaService = new GachaService();
        this.gachaController = new GachaController(player, gachaService);
        this.inventoryController = new InventoryController(player);
        
        // Create main view with logout callback
        MainView mainView = new MainView(player, gachaController, inventoryController, userManager, this::logoutAndShowLogin);
        Scene scene = new Scene(mainView.getRoot(), 900, 650);
        
        // Add CSS
        try {
            scene.getStylesheets().add(getClass().getResource("/com/tugasbesar/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS file not found, using default styling.");
        }
        
        primaryStage.setTitle("Fantasy Gacha Game - " + player.getName());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void logoutAndShowLogin() {
        // Close current window and show login
        Platform.runLater(() -> {
            // Clear current scene
            primaryStage.close();
            
            // Create new stage for login
            Stage newStage = new Stage();
            MainApp newApp = new MainApp();
            newApp.start(newStage);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}