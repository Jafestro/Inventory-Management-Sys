package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a supplier.
 */
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

    /**
     * Constructor for Suppliers.
     * 
     * @param supplierName the name of the supplier
     * @param contactEmail the contact email of the supplier
     */
    public Suppliers(String supplierName, String contactEmail) {
        super();
        this.supplierName = supplierName;
        this.contactEmail = contactEmail;
    }

    /**
     * Default constructor for Suppliers.
     */
    public Suppliers() {

    }

    /**
     * Gets the supplier ID.
     * 
     * @return the supplier ID
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * Sets the supplier ID.
     * 
     * @param supplierID the supplier ID to set
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * Gets the supplier name.
     * 
     * @return the supplier name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * Sets the supplier name.
     * 
     * @param supplierName the supplier name to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * Gets the contact email of the supplier.
     * 
     * @return the contact email of the supplier
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact email of the supplier.
     * 
     * @param contactEmail the contact email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * Returns a string representation of the supplier.
     * 
     * @return a string representation of the supplier
     */
    @Override
    public String toString() {
        return "Suppliers [supplierID=" + supplierID + ", supplierName=" + supplierName + ", contactEmail=" + contactEmail + "]";
    }
}
