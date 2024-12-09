package com.reppuhallinta.inventory_management_sys.controller;

import java.sql.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import session.CustomSessionManager;

/**
 * Controller class for managing the transaction view.
 */
@Controller
public class TransactionViewController extends LogoutController {
    private final TransactionService transactionService;

    private final UserService userService;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private Button productsButton;

    @FXML
    private Button editTransactionButton;

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
    private ProgressBar autoRefreshProgressBar;

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    private Timeline timeline;

    /**
     * Constructor for TransactionViewController.
     * 
     * @param transactionService the service for managing transactions
     * @param userService the service for managing users
     */
    public TransactionViewController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null && !"admin".equals(user.getAccessLevel())) {
            editTransactionButton.setDisable(true);
        } 
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTransactionList(newValue));

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

        if (timeline != null) {
            timeline.stop();
            timeline.setCycleCount(0);
        }

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    loadTransactionData();
                    updateProgressBar();
                }),
                new KeyFrame(Duration.seconds(15))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        UIUtils.setLanguageChoiceBox(languageChoiceBox, "Transactions", "/Transactions.fxml", 1050, 600);
    }

    /**
     * Adds a sort event handler to the given table column.
     * 
     * @param column the table column to add the sort event handler to
     * @param <T> the type of the column
     */
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

    /**
     * Filters the transaction list based on the search word.
     * 
     * @param searchWord the search word to filter the transaction list
     */
    private void filterTransactionList(String searchWord) {
        // Implement filtering logic
    }

    /**
     * Loads the transaction data and sets it to the table.
     */
    private void loadTransactionData() {

        List<Transaction> transactions = transactionService.getAllTransactions();

        List<Integer> userIds = transactions.stream()
                .map(Transaction::getUserId)
                .distinct()
                .collect(Collectors.toList());

        List<User> users = userService.getUsersByIds(userIds);

        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        List<Transaction> transactionsWithUsernames = transactions.stream().map(transaction -> {
            User user = userMap.get(transaction.getUserId());
            transaction.setUsername(user != null ? user.getUsername() : "Unknown");
            return transaction;
        }).collect(Collectors.toList());

        ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList(transactionsWithUsernames);
        transactionTable.setItems(transactionObservableList);

    }
    /**
     * Handles the action when the products button is clicked.
     */
    public void handleProductsButton() {
        Stage stage = (Stage) productsButton.getScene().getWindow();
        UIUtils.loadFXML("/Products.fxml", stage, "Products", 1370, 600, null);
    }

    /**
     * Updates the progress bar.
     */
    private void updateProgressBar() {
        Timeline progressBarTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(autoRefreshProgressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(15), new KeyValue(autoRefreshProgressBar.progressProperty(), 1))
        );
        progressBarTimeline.setCycleCount(1);
        progressBarTimeline.play();
    }

    /**
     * Handles the action when the edit transaction button is clicked.
     */
    @FXML
    private void handleEditTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction == null) {
            UIUtils.showAlert(Alert.AlertType.WARNING, "alert.warning", null, "warning.selectTransactionEdit");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditTransaction.fxml"));
            fxmlLoader.setControllerFactory(UIUtils.getSpringContext()::getBean);
            String localeString = UIUtils.getLocale();
            fxmlLoader.setResources(ResourceBundle.getBundle("bundle_" + localeString, Locale.forLanguageTag(localeString)));
            Parent root = fxmlLoader.load();
            EditTransactionController editTransactionController = fxmlLoader.getController();
            editTransactionController.setTransaction(selectedTransaction);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 350));
            stage.setTitle("Edit Transaction");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
