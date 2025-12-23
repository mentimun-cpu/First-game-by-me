package com.tugasbesar.view;

import com.tugasbesar.service.UserManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    private UserManager userManager;
    private Runnable onLoginSuccess;
    
    public LoginView(Stage stage, UserManager userManager, Runnable onLoginSuccess) {
        this.stage = stage;
        this.userManager = userManager;
        this.onLoginSuccess = onLoginSuccess;
    }
    
    public void show() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63);");
        
        // Title
        Label title = new Label("🎮 Fantasy Gacha Game");
        title.setFont(new Font("Arial", 32));
        title.setTextFill(Color.WHITE);
        
        // Login Form
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(10);
        form.setAlignment(Pos.CENTER);
        
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.WHITE);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(200);
        
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(200);
        
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4A00E0; -fx-text-fill: white; -fx-font-weight: bold;");
        loginButton.setPrefWidth(200);
        
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #8E2DE2; -fx-text-fill: white;");
        registerButton.setPrefWidth(200);
        
        Button guestButton = new Button("Play as Guest");
        guestButton.setStyle("-fx-background-color: #00B4D8; -fx-text-fill: white;");
        guestButton.setPrefWidth(200);
        
        // Add to form
        form.add(usernameLabel, 0, 0);
        form.add(usernameField, 1, 0);
        form.add(passwordLabel, 0, 1);
        form.add(passwordField, 1, 1);
        form.add(messageLabel, 1, 2, 2, 1);
        form.add(loginButton, 1, 3);
        form.add(registerButton, 1, 4);
        form.add(guestButton, 1, 5);
        
        // Event Handlers
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill all fields!");
                return;
            }
            
            if (userManager.login(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");
                onLoginSuccess.run();
            } else {
                messageLabel.setText("Invalid username or password!");
            }
        });
        
        registerButton.setOnAction(e -> showRegisterDialog());
        guestButton.setOnAction(e -> {
            userManager.register("guest_" + System.currentTimeMillis(), "guest", "guest@gacha.com");
            userManager.login("guest_" + System.currentTimeMillis(), "guest");
            onLoginSuccess.run();
        });
        
        root.getChildren().addAll(title, form);
        
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Fantasy Gacha Game - Login");
        stage.show();
    }
    
    private void showRegisterDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Register New Account");
        dialog.setHeaderText("Create your account");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm Password");
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email (optional)");
        
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Confirm:"), 0, 2);
        grid.add(confirmField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();
                String confirm = confirmField.getText();
                String email = emailField.getText().trim();
                
                if (username.isEmpty() || password.isEmpty()) {
                    showAlert("Error", "Username and password are required!");
                    return;
                }
                
                if (!password.equals(confirm)) {
                    showAlert("Error", "Passwords don't match!");
                    return;
                }
                
                if (email.isEmpty()) {
                    email = username + "@gacha.com";
                }
                
                if (userManager.register(username, password, email)) {
                    showAlert("Success", "Account created successfully!");
                } else {
                    showAlert("Error", "Username already exists!");
                }
            }
        });
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}