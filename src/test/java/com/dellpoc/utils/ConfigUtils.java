package com.dellpoc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class ConfigUtils {

    private static final String ENCRYPTION_KEY = "your-encryption-key"; // Use a secure key

    /**
     * Loads properties from an encrypted file.
     * @param filePath The path to the encrypted properties file.
     * @return A Properties object containing the decrypted properties.
     * @throws Exception If an error occurs while loading or decrypting the properties.
     */
    public static Properties loadEncryptedProperties(String filePath) throws Exception {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        Properties decryptedProperties = new Properties();
        for (String key : properties.stringPropertyNames()) {
            String encryptedValue = properties.getProperty(key);
            String decryptedValue = decrypt(encryptedValue);
            decryptedProperties.setProperty(key, decryptedValue);
        }
        return decryptedProperties;
    }

    /**
     * Decrypts an encrypted string.
     * @param encryptedText The encrypted string.
     * @return The decrypted string.
     * @throws Exception If an error occurs during decryption.
     */
    private static String decrypt(String encryptedText) throws Exception {
        SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encryptedText));
        return new String(decryptedBytes);
    }
}
