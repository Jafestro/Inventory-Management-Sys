package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import session.CustomSessionManager;

public abstract class LogoutController {

    @FXML
    protected Button logOutButton;

    @FXML
    public void handleLogOutButtonAction() {
        CustomSessionManager.removeAttribute("user");
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        UIUtils.loadFXML("/Login.fxml", stage, "Login", 400, 350, null);
    }
}
