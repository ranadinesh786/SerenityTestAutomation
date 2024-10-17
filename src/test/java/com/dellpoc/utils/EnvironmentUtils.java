package com.dellpoc.utils;

import java.util.Properties;

public class EnvironmentUtils {

    private static Properties properties;

    static {
        try {
            String env = System.getProperty("env", "dev");
            String filePath = String.format("src/test/resources/%s.properties", env);
            properties = ConfigUtils.loadEncryptedProperties(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment properties", e);
        }
    }

    /**
     * Retrieves the value of a property.
     * @param key The name of the property.
     * @return The value of the property.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
