package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

@SpringBootApplication
public class InventoryManagementSysApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(InventoryManagementSysApplication.class);
        UIUtils.setSpringContext(springContext);
    }

    @Override
    public void start(Stage primaryStage) {
        UIUtils.loadFXML("/Login.fxml", primaryStage, "Login", 400, 350, null);
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}