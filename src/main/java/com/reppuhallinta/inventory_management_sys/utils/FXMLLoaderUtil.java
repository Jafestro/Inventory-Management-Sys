package com.reppuhallinta.inventory_management_sys.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

public class FXMLLoaderUtil {

    @Setter
    private static ApplicationContext springContext;

    public static void loadFXML(String fxmlPath, Stage stage, String title, double v, double v1) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                FXMLLoaderUtil.class.getResource(fxmlPath)
            );
            fxmlLoader.setControllerFactory(springContext::getBean);
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
}
