package com.reppuhallinta.inventory_management_sys.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class FXMLLoaderUtil {
    private static ApplicationContext springContext;

    public static void setSpringContext(ApplicationContext context) {
        springContext = context;
    }

    public static void loadFXML(String fxmlPath, Stage stage, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FXMLLoaderUtil.class.getResource(fxmlPath));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);

            System.out.println("Loading FXML " + fxmlPath);
            System.out.println("Scene " + scene);
            System.out.println("Stage " + stage);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}