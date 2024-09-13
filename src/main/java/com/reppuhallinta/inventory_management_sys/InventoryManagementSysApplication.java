package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.utils.FXMLLoaderUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InventoryManagementSysApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(InventoryManagementSysApplication.class);
        FXMLLoaderUtil.setSpringContext(springContext);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoaderUtil.loadFXML("/Login.fxml", primaryStage, "Login");
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}