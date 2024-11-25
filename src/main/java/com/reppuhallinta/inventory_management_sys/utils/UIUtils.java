package com.reppuhallinta.inventory_management_sys.utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lombok.Setter;

public class UIUtils {

    private static final String ENGLISHSTRING = "English";
    private static final String FINNISHSTRING = "Finnish";
    private static final String JAPANESESTRING = "Japanese";
    private static final String AZERBAIJANSTRING = "Azerbaijani";

    private static final String ENGLISHFI = "Englanti";
    private static final String FINNISHFI = "Suomi";
    private static final String JAPANESEFI = "Japani";
    private static final String AZERBAIJANFI = "Azerbaijan";

    private static final String ENGLISHAZ = "İngilis dili";
    private static final String FINNISHAZ = "Fin dili";
    private static final String JAPANESEAZ = "Yapon dili";
    private static final String AZERBAIJANAZ = "Azərbaycan dili";

    private static final String ENGLISHJP = "英語";
    private static final String FINNISHJP = "フィンランド語";
    private static final String JAPANESEJP = "日本語";
    private static final String AZERBAIJANJP = "アゼルバイジャン語";

    @Setter
    private static ApplicationContext springContext;

    private static String localeString = "EN";

    private UIUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static void setLocale(String locale) {
        localeString = locale;
        LanguageContext.setLanguage(locale); // Update the ThreadLocal context
    }

    public static String getLocale() {
        return localeString;
    }

    private static List<String> languagesEnglish = List.of(ENGLISHSTRING, FINNISHSTRING, JAPANESESTRING, AZERBAIJANSTRING);

    private static List<String> langaugesFinnish = List.of(ENGLISHFI, FINNISHFI, JAPANESEFI, AZERBAIJANFI);

    private static List<String> languagesAzerbaijan = List.of(ENGLISHAZ, FINNISHAZ, JAPANESEAZ,
            AZERBAIJANAZ);

    private static List<String> languagesJapanese = List.of(ENGLISHJP, FINNISHJP, JAPANESEJP, AZERBAIJANJP);

    public static final String ERRORTITLE = "alert.error";
    public static final String VALIDATETITLE = "alert.validationError";
    public static final String SUCCESSTITLE = "alert.success";

    private static final Logger logger = LoggerFactory.getLogger(UIUtils.class);

    public static void setLanguageChoiceBox(ChoiceBox<String> languageChoiceBox, String titleToSet, String fxmlPath,
            double currentWidth, double currentHeight) {
        String language = UIUtils.getLocale();
        List<String> languages = switch (language) {
            case "FI" -> langaugesFinnish;
            case "EN" -> languagesEnglish;
            case "AZ" -> languagesAzerbaijan;
            case "JP" -> languagesJapanese;
            default -> languagesEnglish;
        };
        languageChoiceBox.getItems().addAll(languages);
        switch (UIUtils.getLocale()) {
            case "EN":
                languageChoiceBox.setValue(ENGLISHSTRING);
                break;
            case "FI":
                languageChoiceBox.setValue(FINNISHFI);
                break;
            case "JP":
                languageChoiceBox.setValue(JAPANESEJP);
                break;
            case "AZ":
                languageChoiceBox.setValue(AZERBAIJANAZ);
                break;
            default:
                languageChoiceBox.setValue(ENGLISHSTRING);
                break;
        }
        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(ENGLISHSTRING) || newValue.equals(ENGLISHFI) || newValue.equals(ENGLISHJP)
                    || newValue.equals(ENGLISHAZ)) {
                UIUtils.setLocale("EN");
            } else if (newValue.equals(FINNISHSTRING) || newValue.equals(FINNISHFI) || newValue.equals(FINNISHJP)
                    || newValue.equals(FINNISHAZ)) {
                UIUtils.setLocale("FI");
            } else if (newValue.equals(JAPANESESTRING) || newValue.equals(JAPANESEFI) || newValue.equals(JAPANESEJP)
                    || newValue.equals(JAPANESEAZ)) {
                UIUtils.setLocale("JP");
            } else if (newValue.equals(AZERBAIJANSTRING) || newValue.equals(AZERBAIJANFI) || newValue.equals(AZERBAIJANJP)
                    || newValue.equals(AZERBAIJANAZ)) {
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
                    UIUtils.class.getResource(fxmlPath));

            fxmlLoader.setResources(ResourceBundle.getBundle("bundle_" + localeString, Locale.forLanguageTag(localeString)));

            fxmlLoader.setControllerFactory(springContext::getBean);

            if (controller != null) {
                fxmlLoader.setController(controller);
            }

            Scene scene = new Scene(fxmlLoader.load(), v, v1);

            logger.info("Loading FXML {}", fxmlPath);
            logger.info("Scene {}", scene);
            logger.info("Stage {}", stage);

            ResourceBundle bundle = ResourceBundle.getBundle("bundle_" + localeString, Locale.forLanguageTag(localeString));

            switch (title) {
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
                default:
                    stage.setTitle(title);
                    break;
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Error loading FXML {}", fxmlPath + ": " + e.getMessage());
        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", Locale.forLanguageTag(localeString));
        logger.info("Bundle: {}", bundle);
        logger.info("Title: {}", title);
        logger.info("Header: {}", headerText);
        logger.info("Content: {}", contentText);

        Alert alert = new Alert(alertType);
        alert.setTitle(bundle.getString(title));
        alert.setHeaderText(headerText != null ? bundle.getString(headerText) : null);
        alert.setContentText(bundle.getString(contentText));
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
