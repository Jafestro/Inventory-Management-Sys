package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<Suppliers> createSupplier(@RequestBody Suppliers supplier) {
        Suppliers createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suppliers> getSupplierById(@PathVariable int id) {
        Suppliers supplier = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Suppliers> updateSupplier(@PathVariable int id, @RequestBody Suppliers supplierDetails) {
        Suppliers updatedSupplier = supplierService.updateSupplier(id, supplierDetails);

        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
