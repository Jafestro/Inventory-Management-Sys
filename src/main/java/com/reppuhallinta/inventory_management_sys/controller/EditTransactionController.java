package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.ERRORTITLE;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Controller class for editing transactions.
 */
@Controller
public class EditTransactionController {

    private final TransactionService transactionService;

    private final UserService userService;

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

    /**
     * Constructor for EditTransactionController.
     * 
     * @param transactionService the service for managing transactions
     * @param userService the service for managing users
     */
    public EditTransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    /**
     * Sets the transaction to be edited and pre-fills the data.
     * 
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        prefillData(); // Call prefillData after setting the transaction
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        users = userService.getAllUsers();

        for (User user : users) {
            usernameComboBox.getItems().add(user.getUsername());
        }

        String localeString = UIUtils.getLocale();

        ResourceBundle bundle = ResourceBundle.getBundle("bundle_" + localeString, Locale.forLanguageTag(localeString));

        transactionTypeComboBox.getItems().add(bundle.getString("transaction.type.add"));
        transactionTypeComboBox.getItems().add(bundle.getString("transaction.type.update"));

    }

    /**
     * Handles the action when the update transaction button is clicked.
     */
    @FXML
    public void updateTransactionButtonOnAction() {
        String quantity = quantityField.getText();
        String transactionType = transactionTypeComboBox.getValue();
        String username = usernameComboBox.getValue();
        
        if (quantity.isEmpty() || transactionType == null || username == null) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.fillAllFields");
            return;
        }

        int quantityInt;

        try {
            quantityInt = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.quantityValidNumber");
            return;
        }

        if (quantityInt <= 0) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.invalidQuantity");
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

        UIUtils.showAlert(Alert.AlertType.INFORMATION, "alert.success", null, "success.transactionUpdated");

        UIUtils.closeWindow(editButton);
    }

    /**
     * Pre-fills the data for the transaction to be edited.
     */
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