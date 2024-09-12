package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.InventoryManagementSysApplication;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import session.CustomSessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private InventoryManagementSysApplication application;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public void setApplication(InventoryManagementSysApplication application) {
        this.application = application;
    }

    public void initialize() {
        usernameField.setPromptText("Enter your username");
        passwordField.setPromptText("Enter your password");
    }

    @FXML
    public void handleLogin() {
        Window owner = loginButton.getScene().getWindow();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter username and password!");
            return;
        }

        User user = userService.authenticate(username, password);

        if (user != null) {
            Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
            successAlert.setTitle("Login Successful!");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Welcome " + usernameField.getText() + "!");
            successAlert.initOwner(owner);
            successAlert.showAndWait();

            try {
                // Regenerate session ID for new login
                CustomSessionManager.regenerateSessionId();

                // Set session attributes using CustomSessionManager
                CustomSessionManager.setAttribute("user", user);

                // Access the session ID
                String sessionId = CustomSessionManager.getSessionId();
                System.out.println("Session ID: " + sessionId);
            } catch (Exception e) {
                System.out.println("Something with session id went wrong, here is stacktrace" + e.getMessage());
                showAlert(Alert.AlertType.ERROR, owner, "Session Error", "Failed to set session attributes.");
                return;
            }

            application.switchScene("/Products.fxml");
            // Close login window
            Stage stage = (Stage) owner;
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "Login failed", "Invalid username or password");
        }
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}