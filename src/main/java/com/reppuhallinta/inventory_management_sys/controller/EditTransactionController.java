package com.reppuhallinta.inventory_management_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.service.TransactionService;

import javafx.fxml.FXML;

@Controller
public class EditTransactionController {
    
    @Autowired
    private TransactionService transactionService;



    @FXML
    public void initialize() {
        // TODO: Implement this method
    }


    @FXML
    public void updateTransactionButtonOnAction() {
        // TODO: Implement this method
    }


}
