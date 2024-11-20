package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.service.TransactionService;

import javafx.fxml.FXML;
import session.CustomSessionManager;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Controller
public class EditTransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @FXML
    private ComboBox<String> transactionTypeComboBox;

    @FXML
    private ComboBox<String> usernameComboBox;

    @FXML
    private TextField quantityField;

    @FXML
    private Button editButton;

    private List<User> users;

    private Transaction transaction;

    private User user;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        prefillData(); // Call prefillData after setting the transaction
    }

    private enum transactionType {
        ADD, UPDATE
    }

    @FXML
    public void initialize() {
        users = userService.getAllUsers();

        for (User user : users) {
            usernameComboBox.getItems().add(user.getUsername());
        }

        String localeString = UIUtils.getLocale();

        ResourceBundle bundle = ResourceBundle.getBundle("bundle_" + localeString, new Locale(localeString));

        transactionTypeComboBox.getItems().add(bundle.getString("transaction.type.add"));
        transactionTypeComboBox.getItems().add(bundle.getString("transaction.type.update"));

        //transactionTypeComboBox.getItems().add(transactionType.ADD.name());
        //transactionTypeComboBox.getItems().add(transactionType.UPDATE.name());

        user = (User) CustomSessionManager.getAttribute("user");

    }

    @FXML
    public void updateTransactionButtonOnAction() {
        String quantity = quantityField.getText();
        String transactionType = transactionTypeComboBox.getValue();
        String username = usernameComboBox.getValue();
        
        if (quantity.isEmpty() || transactionType == null || username == null) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.fillAllFields");
            return;
        }

        int quantityInt;

        try {
            quantityInt = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR,"alert.error", null, "error.quantityValidNumber");
            return;
        }

        if (quantityInt <= 0) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.invalidQuantity");
            return;
        }

        User newUser = userService.findUserByUsername(username);

        if (newUser == null) {
            return;
        }

        transaction.setTransactionType(transactionTypeComboBox.getValue());

        transaction.setQuantity(quantityInt);
        transaction.setUserId(newUser.getId());

        transactionService.updateTransaction(transaction.getTransactionId(), transaction);

        System.out.println("Transaction updated successfully: " + transaction);

        UIUtils.showAlert(Alert.AlertType.INFORMATION, "alert.success", null, "success.transactionUpdated");

        UIUtils.closeWindow(editButton);
    }

    private void prefillData() {
        if (transaction != null) {
            quantityField.setText(String.valueOf(transaction.getQuantity()));
            transactionTypeComboBox.setValue(transaction.getTransactionType());

            for (User user1 : users) {
                if (transaction.getUserId() == user1.getId()) {
                    usernameComboBox.setValue(user1.getUsername());
                }
            }
        }
    }
}