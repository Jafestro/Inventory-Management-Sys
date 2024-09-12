package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InventoryManagementSysApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(InventoryManagementSysApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
    
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory Management System");

        // Center the stage on the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);

        primaryStage.show();

        LoginController loginController = fxmlLoader.getController();
        loginController.setApplication(this);
    }

    public void switchScene(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            fxmlLoader.setControllerFactory(springContext::getBean);
            Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Inventory Management System");

			// Center the new stage on the screen
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}