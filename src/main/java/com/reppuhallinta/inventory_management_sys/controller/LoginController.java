package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.control.*;
import session.CustomSessionManager;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private ChoiceBox languageChoiceBox;


    @FXML
    public void initialize (){
        languageChoiceBox.getItems().addAll("English", "Finnish", "Japanese", "Azerbaijani");
        switch (UIUtils.getLocale()) {
            case "EN":
                languageChoiceBox.setValue("English");
                break;
            case "FI":
                languageChoiceBox.setValue("Finnish");
                break;
            case "JA":
                languageChoiceBox.setValue("Japanese");
                break;
            case "AZ":
                languageChoiceBox.setValue("Azerbaijani");
                break;
        }

        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("English")) {
                UIUtils.setLocale("EN");
            } else if (newValue.equals("Finnish")) {
                UIUtils.setLocale("FI");
            } else if (newValue.equals("Japanese")) {
                UIUtils.setLocale("JP");
            } else if (newValue.equals("Azerbaijani")) {
                UIUtils.setLocale("AZ");
            } else {
                UIUtils.setLocale("EN");
            }
            reloadLoginForm();
        });
    }


    private void reloadLoginForm() {
        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        UIUtils.loadFXML("/Login.fxml", stage, "Login", 400, 350, null);
    }

    @FXML
    public void handleLogin() {
        Window owner = loginButton.getScene().getWindow();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter username and password!");
            return;
        }

        User user = userService.authenticate(username, password);

        if (user != null) {
            // UIUtils.showAlert(Alert.AlertType.INFORMATION, "Success", null, "You are logged in!");

            try {
                CustomSessionManager.regenerateSessionId();

                CustomSessionManager.setAttribute("user", user);

                String sessionId = CustomSessionManager.getSessionId();
                System.out.println("Session ID: " + sessionId);
            } catch (Exception e) {
                System.out.println("Something with session id went wrong, here is stacktrace" + e.getMessage());
                UIUtils.showAlert(Alert.AlertType.ERROR, "Session Error", null, "Failed to set session attributes.");
                return;
            }

            Stage stage = (Stage) owner;
            UIUtils.loadFXML("/Products.fxml", stage, "Products", 1370, 600, null);
        } else {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Login fail", null, "Invalid username or password");
        }
    }

    @FXML
    public void handleLoginLink() {
        Stage stage = (Stage) registerLink.getScene().getWindow();
        UIUtils.loadFXML("/Register.fxml", stage, "Register", 700, 500, null);
    }
}