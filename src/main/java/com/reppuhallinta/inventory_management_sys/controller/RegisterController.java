package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterController {


    @Autowired
    private UserService userService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button registerButton;

    @FXML
    public void handleRegister() {
        String username = this.usernameField.getText();
        String firstName = this.firstnameField.getText();
        String lastName = this.lastnameField.getText();
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();

        if (!validateFields(username, firstName, lastName, password, confirmPassword)) {
            return;
        }

        try {
            User userCheck = userService.findUserByUsername(username);

            if (userCheck != null) {
                UIUtils.showAlert(Alert.AlertType.ERROR, "Error",  null, "Username already exists!");
                return;
            }

            User user = new User(username, firstName, lastName, password, "user");

            userService.createUser(user);

            Stage stage = (Stage) registerButton.getScene().getWindow();
            UIUtils.showAlert(Alert.AlertType.INFORMATION, "Success",  null, "User registered successfully");
            UIUtils.loadFXML("/Login.fxml", new Stage(), "Login", 400, 350, null);
            stage.close();
        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error",  null, "An error occurred while registering user");
        }
    }

    private boolean validateFields(String username, String firstName, String lastName, String password, String confirmPassword) {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error",  null, "Please fill all the fields");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error",  null, "Passwords do not match");
            return false;
        }

        return true;
    }

    @FXML
    public void handleLoginLink() {
        Stage stage = (Stage) registerButton.getScene().getWindow();   
        UIUtils.loadFXML("/Login.fxml", new Stage(), "Login", 400, 350, null);
        stage.close();
    }
}
