package com.reppuhallinta.inventory_management_sys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/reppuhallinta/inventory_management_sys/view/Products.fxml"));
		fxmlLoader.setControllerFactory(springContext::getBean);
		Scene scene = new Scene(fxmlLoader.load());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Inventory Management System");
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/*public static void main(String[] args) {
		SpringApplication.run(InventoryManagementSysApplication.class, args);
	}*/

}
