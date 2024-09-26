package com.reppuhallinta.inventory_management_sys.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import session.CustomSessionManager;

@Controller
public class TransactionViewController extends LogoutController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private Button productsButton;

    @FXML
    private TableColumn<Transaction, Integer> idColumn;

    @FXML
    private TableColumn<Transaction, Date> dateColumn;

    @FXML
    private TableColumn<Transaction, String> typeColumn;

    @FXML
    private TableColumn<Transaction, Integer> quantityColumn;

    @FXML
    private TableColumn<Transaction, Integer> productIdColumn;

    @FXML
    private TableColumn<Transaction, String> usernameColumn;

    @FXML
    private TextField searchField;


    @FXML
    public void initialize() {

        String sessionId = CustomSessionManager.getSessionId();
        System.out.println("Current session id: " + sessionId);

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null) {
            System.out.println("Current user id: " + user.getId());
        } else {
            System.out.println("No user logged in");
        }

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTransactionList(newValue);
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));



        // Add sort event handlers
        addSortEventHandler(idColumn);
        addSortEventHandler(dateColumn);
        addSortEventHandler(typeColumn);
        addSortEventHandler(quantityColumn);
        addSortEventHandler(productIdColumn);
        addSortEventHandler(usernameColumn);

        loadTransactionData();
    }
    private <T> void addSortEventHandler(TableColumn<Transaction, T> column) {
        Label label = new Label();
        column.setGraphic(label);
        label.setOnMouseClicked(event -> {
            if (column.getSortType() == TableColumn.SortType.ASCENDING) {
                column.setSortType(TableColumn.SortType.DESCENDING);
            } else {
                column.setSortType(TableColumn.SortType.ASCENDING);
            }
            transactionTable.getSortOrder().clear();
            transactionTable.getSortOrder().add(column);
        });
    }


    private void filterTransactionList(String searchWord){
        ObservableList<Transaction> filteredList = FXCollections.observableArrayList();

            for (Transaction transaction : transactionService.getAllTransactions()) {
                if (String.valueOf(transaction.getTransactionId()).contains(searchWord.toLowerCase())) {
                    filteredList.add(transaction);
                }
            }

            transactionTable.setItems(filteredList);
    }

    private void loadTransactionData() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<Transaction> transactionsWithUsernames = transactions.stream().map(transaction -> {
            User user = userService.getUserById(transaction.getUserId());
            transaction.setUsername(user != null ? user.getUsername() : "Unknown");
            return transaction;
        }).collect(Collectors.toList());

        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactionsWithUsernames);
        transactionTable.setItems(transactionObservableList);
    }

    public void handleProductsButton() {
        Stage stage = (Stage) productsButton.getScene().getWindow();

        UIUtils.loadFXML("/Products.fxml", stage, "Products", 1200, 1200, null);
    }

}
