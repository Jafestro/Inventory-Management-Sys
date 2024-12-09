package com.reppuhallinta.inventory_management_sys.controller;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.ERRORTITLE;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import session.CustomSessionManager;

/**
 * Controller class for managing user login.
 */
@Controller
public class LoginController {

    private final UserService userService;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    private static final String TITLETOSET = "Login";

    /**
     * Constructor for LoginController.
     * 
     * @param userService the service for managing users
     */
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize (){
        UIUtils.setLanguageChoiceBox(languageChoiceBox, TITLETOSET, "/Login.fxml", 400, 350);
    }

    /**
     * Handles the login process.
     */
    @FXML
    public void handleLogin() {
        Window owner = loginButton.getScene().getWindow();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.enterUsernameAndPassword");
            return;
        }

        User user = userService.authenticate(username, password);

        if (user != null) {
            try {
                CustomSessionManager.regenerateSessionId();

                CustomSessionManager.setAttribute("user", user);

            } catch (Exception e) {
                UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.generic");
                return;
            }

            Stage stage = (Stage) owner;
            UIUtils.loadFXML("/Products.fxml", stage, "Products", 1370, 600, null);
        } else {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.invalidUsernameAndPassword");
        }
    }

    /**
     * Handles the action when the register link is clicked.
     */
    @FXML
    public void handleLoginLink() {
        Stage stage = (Stage) registerLink.getScene().getWindow();
        UIUtils.loadFXML("/Register.fxml", stage, "Register", 700, 500, null);
    }
}