package com.reppuhallinta.inventory_management_sys.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class UIUtils {
    
    @Setter
    private static ApplicationContext springContext;

    private static String localeString = "EN";

    public static void setLocale(String locale) {
        localeString = locale;
    }

    public static String getLocale() {
        return localeString;
    }

    private static List<String> languagesEnglish = List.of("English", "Finnish", "Japanese", "Azerbaijani");

    private static List<String> langaugesFinnish = List.of("Englanti", "Suomi", "Japani", "Azerbaijan");

    private static List<String> languagesAzerbaijan = List.of("İngilis dili","Fin dili", "Yapon dili", "Azərbaycan dili");

    private static List<String> languagesJapanese = List.of("英語", "フィンランド語", "日本語", "アゼルバイジャン語");

    private static String language;

    public static void setLanguageChoiceBox(ChoiceBox languageChoiceBox, String titleToSet, String fxmlPath, double currentWidth, double currentHeight) {
        language = UIUtils.getLocale();
        List <String> languages = switch (language) {
            case "FI" -> langaugesFinnish;
            case "EN" -> languagesEnglish;
            case "AZ" -> languagesAzerbaijan;
            case "JP" -> languagesJapanese;
            default -> languagesEnglish;
        };
        languageChoiceBox.getItems().addAll(languages);
        switch (UIUtils.getLocale()) {
            case "EN":
                languageChoiceBox.setValue("English");
                break;
            case "FI":
                languageChoiceBox.setValue("Suomi");
                break;
            case "JP":
                languageChoiceBox.setValue("日本語");
                break;
            case "AZ":
                languageChoiceBox.setValue("Azərbaycan dili");
                break;
        }
        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("English") || newValue.equals("Englanti") || newValue.equals("英語") || newValue.equals("İngilis dili"))  {
                UIUtils.setLocale("EN");
            } else if (newValue.equals("Finnish") || newValue.equals("Suomi") || newValue.equals("フィンランド語") || newValue.equals("Fin dili")) {
                UIUtils.setLocale("FI");
            } else if (newValue.equals("Japanese") || newValue.equals("Japani") || newValue.equals("日本語") || newValue.equals("Yapon dili")) {
                UIUtils.setLocale("JP");
            } else if (newValue.equals("Azerbaijani") || newValue.equals("Azerbaijan") || newValue.equals("アゼルバイジャン語") || newValue.equals("Azərbaycan dili")) {
                UIUtils.setLocale("AZ");
            } else {
                UIUtils.setLocale("EN");
            }

            Stage stage = (Stage) languageChoiceBox.getScene().getWindow();

            UIUtils.loadFXML(fxmlPath, stage, titleToSet, currentWidth, currentHeight, null);
        });
    }

    public static void loadFXML(String fxmlPath, Stage stage, String title, double v, double v1, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    UIUtils.class.getResource(fxmlPath)
            );

            fxmlLoader.setResources(ResourceBundle.getBundle("bundle_" + localeString, new Locale(localeString)));

            fxmlLoader.setControllerFactory(springContext::getBean);

            if (controller != null) {
                fxmlLoader.setController(controller);
            }

            Scene scene = new Scene(fxmlLoader.load(), v, v1);

            System.out.println("Loading FXML " + fxmlPath);
            System.out.println("Scene " + scene);
            System.out.println("Stage " + stage);

            ResourceBundle bundle = ResourceBundle.getBundle("bundle_" + localeString, new Locale(localeString));

            switch(title) {
                case "Login":
                    stage.setTitle(bundle.getString("title.login"));
                    break;
                case "Register":
                    stage.setTitle(bundle.getString("title.register"));
                    break;
                case "Products":
                    stage.setTitle(bundle.getString("title.products"));
                    break;
                case "Create Product":
                    stage.setTitle(bundle.getString("title.createProduct"));
                    break;
                case "Edit Product":
                    stage.setTitle(bundle.getString("title.editProduct"));
                    break;
                case "Transactions":
                    stage.setTitle(bundle.getString("title.transactions"));
                    break;
                case "Reports":
                    stage.setTitle(bundle.getString("title.reports"));
                    break;
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML " + fxmlPath + ": " + e.getMessage());
        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    public static ApplicationContext getSpringContext() {
        return springContext;
    }

    public static void closeWindow(Button button) {

        Stage stage = (Stage) button.getScene().getWindow();

        stage.close();

    }
}
