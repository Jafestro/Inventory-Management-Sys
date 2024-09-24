package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


@Controller
public class ReportViewController {

    @FXML private Button GetAllStockProductReportButton;
    @FXML private Button GetAllTransactionsReportButton;
    @FXML private TextField TransByProductTextField;
    @FXML private Button GetTransactionByProductReportButton;
    @Autowired
    private ProductService productService;

    LocalDateTime notFormattedTime = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDate date = LocalDate.now();
    String time = notFormattedTime.format(myFormatObj);

    @FXML
    private void GetAllStockProductReportButtonAction() {
        List<Products> products = productService.getAllProducts();
        StringBuilder report = new StringBuilder("All stock products " + time + "\n\n");
        report.append(String.format("%-10s %-20s %-10s %-10s %-10s %-10s\n", "ID", "Name", "Price", "Quantity", "Category", "Supplier"));
        report.append("--------------------------------------------------------------------------\n");
        for (Products product : products) {
            report.append(String.format("%-10d %-20s %-10.2f %-10d %-10d %-10d\n",
                    product.getId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategoryId(),
                    product.getSupplierID()));
            report.append("--------------------------------------------------------------------------\n");
        }
        generateDownloadableFile(report.toString());
    }

    private void generateDownloadableFile(String content) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("AllStockProducts " + time, "*.txt"));
        fileChooser.setInitialFileName("AllStockProducts " + date + ".txt");
        Stage stage = (Stage) GetAllStockProductReportButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);;
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}