package com.reppuhallinta.inventory_management_sys.utils;

public class LanguageContext {
    private static final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> "EN");

    public static void setLanguage(String language) {
        contextHolder.set(language);
    }

    public static String getLanguage() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
