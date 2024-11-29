package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import session.CustomSessionManager;

/**
 * Abstract controller class for handling logout functionality.
 */
public abstract class LogoutController {

    @FXML
    protected Button logOutButton;

    /**
     * Handles the action when the logout button is clicked.
     * Removes the user from the session and loads the login screen.
     */
    @FXML
    public void handleLogOutButtonAction() {
        CustomSessionManager.removeAttribute("user");
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        UIUtils.loadFXML("/Login.fxml", stage, "Login", 400, 350, null);
    }
}
