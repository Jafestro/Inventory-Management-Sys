package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Suppliers")
public class Suppliers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierId")
    private int supplierID;

    @Column(name = "Name")
    private String supplierName;

    @Column(name = "ContactEmail")
    private String contactEmail;

    public Suppliers(String supplierName, String contactEmail) {
        super();
        this.supplierName = supplierName;
        this.contactEmail = contactEmail;
    }

    public Suppliers() {

    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "Suppliers [supplierID=" + supplierID + ", supplierName=" + supplierName + ", contactEmail=" + contactEmail
                + "]";
    }
}
