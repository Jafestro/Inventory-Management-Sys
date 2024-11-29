package com.reppuhallinta.inventory_management_sys.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * DataSource implementation for routing based on the current language.
 */
public class LanguageRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * Determines the current lookup key for the DataSource.
     * 
     * @return the current language locale
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // Fetch the current language dynamically
        return com.reppuhallinta.inventory_management_sys.utils.UIUtils.getLocale();
    }
}
