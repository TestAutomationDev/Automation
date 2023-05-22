/**
 * File: PropertyReader.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: A class to read properties files.
 */

package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static Properties properties;

    public PropertyReader(String filePath) {
        properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key) {
        return properties.getProperty(key);
    }
}
