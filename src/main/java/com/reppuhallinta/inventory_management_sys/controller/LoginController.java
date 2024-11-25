package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.control.*;
import session.CustomSessionManager;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.springframework.stereotype.Controller;

import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.ERRORTITLE;

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

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @FXML
    public void initialize (){
        UIUtils.setLanguageChoiceBox(languageChoiceBox, TITLETOSET, "/Login.fxml", 400, 350);
    }

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

    @FXML
    public void handleLoginLink() {
        Stage stage = (Stage) registerLink.getScene().getWindow();
        UIUtils.loadFXML("/Register.fxml", stage, "Register", 700, 500, null);
    }
}