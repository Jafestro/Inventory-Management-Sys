package com.reppuhallinta.inventory_management_sys.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Optional;

public class UIUtils {

    @Setter
    private static ApplicationContext springContext;

    public static void loadFXML(String fxmlPath, Stage stage, String title, double v, double v1, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    UIUtils.class.getResource(fxmlPath)
            );
            fxmlLoader.setControllerFactory(springContext::getBean);

            if (controller != null) {
                fxmlLoader.setController(controller);
            }

            Scene scene = new Scene(fxmlLoader.load(), v, v1);

            System.out.println("Loading FXML " + fxmlPath);
            System.out.println("Scene " + scene);
            System.out.println("Stage " + stage);

            stage.setScene(scene);
            stage.setTitle(title);
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
}
