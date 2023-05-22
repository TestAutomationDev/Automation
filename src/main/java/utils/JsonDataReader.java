/**
 * File: JsonDataReader.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: A class to read JSON files.
 */
package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonDataReader {

    public JsonElement readJsonFile(String filePath) {
        try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }

            Reader reader = new InputStreamReader(inputStream);
            return JsonParser.parseReader(reader);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
