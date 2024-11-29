package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.repository.SupplierRepository;

/**
 * Service class for managing suppliers.
 */
@Service
public class SupplierService {
    
    private final SupplierRepository supplierRepository;

    /**
     * Constructor for SupplierService.
     * 
     * @param supplierRepository the repository for supplier data
     */
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Creates a new supplier.
     * 
     * @param supplier the supplier to create
     * @return the created supplier
     */
    public Suppliers createSupplier(Suppliers supplier) {
        return supplierRepository.save(supplier);
    }

    /**
     * Retrieves all suppliers.
     * 
     * @return a list of all suppliers
     */
    public List<Suppliers> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    /**
     * Retrieves a supplier by its ID.
     * 
     * @param id the ID of the supplier
     * @return the supplier with the given ID, or null if not found
     */
    public Suppliers getSupplierById(int id) {
        return supplierRepository.findById(id).orElse(null); // May need to change this
    }

    /**
     * Updates an existing supplier.
     * 
     * @param id the ID of the supplier to update
     * @param supplierDetails the new details for the supplier
     * @return the updated supplier
     */
    public Suppliers updateSupplier(int id, Suppliers supplierDetails) {
        Suppliers supplier = getSupplierById(id);
        supplier.setSupplierName(supplierDetails.getSupplierName());
        supplier.setContactEmail(supplierDetails.getContactEmail());

        return supplierRepository.save(supplier);
    }

    /**
     * Deletes a supplier by its ID.
     * 
     * @param id the ID of the supplier to delete
     */
    public void deleteSupplier(int id) {
        Suppliers supplier = getSupplierById(id);

        supplierRepository.delete(supplier);
    }
}
