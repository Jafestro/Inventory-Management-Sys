package com.reppuhallinta.inventory_management_sys.utils;

/**
 * Utility class for managing language context using ThreadLocal.
 */
public class LanguageContext {
    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> "EN");

    /**
     * Sets the language for the current thread.
     * 
     * @param language the language to set
     */
    public static void setLanguage(String language) {
        contextHolder.set(language);
    }

    /**
     * Retrieves the language for the current thread.
     * 
     * @return the current language
     */
    public static String getLanguage() {
        return contextHolder.get();
    }

    /**
     * Clears the language context for the current thread.
     */
    public static void clear() {
        contextHolder.remove();
    }
}