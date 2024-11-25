package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.repository.SupplierRepository;

@Service
public class SupplierService {
    
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Suppliers createSupplier(Suppliers supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Suppliers> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Suppliers getSupplierById(int id) {
        return supplierRepository.findById(id).orElse(null); // May need to change this
    }

    public Suppliers updateSupplier(int id, Suppliers supplierDetails) {
        Suppliers supplier = getSupplierById(id);
        supplier.setSupplierName(supplierDetails.getSupplierName());
        supplier.setContactEmail(supplierDetails.getContactEmail());

        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(int id) {
        Suppliers supplier = getSupplierById(id);

        supplierRepository.delete(supplier);
    }
}
