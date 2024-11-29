package com.reppuhallinta.inventory_management_sys.controller;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.ERRORTITLE;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.SUCCESSTITLE;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for managing user registration.
 */
@Controller
public class RegisterController {

    private final UserService userService;

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

    /**
     * Constructor for RegisterController.
     * 
     * @param userService the service for managing users
     */
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles the registration process.
     */
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
                UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE,  null, "error.usernameExists");
                return;
            }

            User user = new User(username, firstName, lastName, password, "user");

            userService.createUser(user);

            Stage stage = (Stage) registerButton.getScene().getWindow();
            UIUtils.showAlert(Alert.AlertType.INFORMATION, SUCCESSTITLE,  null, "success.userRegistered");
            UIUtils.loadFXML("/Login.fxml", new Stage(), "Login", 400, 350, null);
            stage.close();
        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE,  null, "error.generic");
        }
    }

    /**
     * Validates the registration fields.
     * 
     * @param username the username
     * @param firstName the first name
     * @param lastName the last name
     * @param password the password
     * @param confirmPassword the confirmation password
     * @return true if the fields are valid, false otherwise
     */
    private boolean validateFields(String username, String firstName, String lastName, String password, String confirmPassword) {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE,  null, "error.fillAllFields");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE,  null, "error.passwordsDoNotMatch");
            return false;
        }

        return true;
    }

    /**
     * Handles the action when the login link is clicked.
     */
    @FXML
    public void handleLoginLink() {
        Stage stage = (Stage) registerButton.getScene().getWindow();   
        UIUtils.loadFXML("/Login.fxml", new Stage(), "Login", 400, 350, null);
        stage.close();
    }
}
