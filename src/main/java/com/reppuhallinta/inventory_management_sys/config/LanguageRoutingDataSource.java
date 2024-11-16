package com.reppuhallinta.inventory_management_sys.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class LanguageRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Fetch the current language dynamically
        return com.reppuhallinta.inventory_management_sys.utils.UIUtils.getLocale();
    }
}
