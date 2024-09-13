package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import session.CustomSessionManager;

import java.sql.Date;
import java.util.List;

@Controller
public class TransactionViewController {

    @Autowired
    private TransactionService transactionService;

    @FXML
    private TableView<Transaction> transactionTable;

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
    private TableColumn<Transaction, Integer> userIdColumn;


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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        loadTransactionData();
    }

    private void loadTransactionData() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactions);
        transactionTable.setItems(transactionObservableList);
    }

}
