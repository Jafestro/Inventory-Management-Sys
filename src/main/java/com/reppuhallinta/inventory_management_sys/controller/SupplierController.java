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

/**
 * REST controller for managing suppliers.
 */
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    
    private final SupplierService supplierService;

    /**
     * Constructor for SupplierController.
     * 
     * @param supplierService the service for managing suppliers
     */
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Creates a new supplier.
     * 
     * @param supplier the supplier to create
     * @return the created supplier
     */
    @PostMapping
    public ResponseEntity<Suppliers> createSupplier(@RequestBody Suppliers supplier) {
        Suppliers createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    /**
     * Retrieves all suppliers.
     * 
     * @return a list of all suppliers
     */
    @GetMapping
    public List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    /**
     * Retrieves a supplier by its ID.
     * 
     * @param id the ID of the supplier
     * @return the supplier with the given ID, or null if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Suppliers> getSupplierById(@PathVariable int id) {
        Suppliers supplier = supplierService.getSupplierById(id);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    /**
     * Updates an existing supplier.
     * 
     * @param id the ID of the supplier to update
     * @param supplierDetails the new details for the supplier
     * @return the updated supplier
     */
    @PostMapping("/{id}")
    public ResponseEntity<Suppliers> updateSupplier(@PathVariable int id, @RequestBody Suppliers supplierDetails) {
        Suppliers updatedSupplier = supplierService.updateSupplier(id, supplierDetails);

        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    /**
     * Deletes a supplier by its ID.
     * 
     * @param id the ID of the supplier to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
        supplierService.deleteSupplier(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
