/**
 * File: CountryDataProvider.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Provides country data.
 */

package dataproviders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import objects.Country;
import utils.JsonDataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryDataProvider {
    private static final String FILE_PATH = "/testdata/countries_matrix.json";
    private final Random random = new Random();

    private final List<Country> countries = new ArrayList<>(); // Initialize the list to avoid NullPointerException

    public List<Country> getCountries() {
        JsonDataReader jsonDataReader = new JsonDataReader();
        JsonElement jsonElement = jsonDataReader.readJsonFile(FILE_PATH);

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray countryDataArray = jsonObject.getAsJsonArray("countries");

        for (JsonElement countryElement : countryDataArray) {
            JsonObject countryData = countryElement.getAsJsonObject();
            String name = countryData.get("name").getAsString();
            boolean marketingPreference = countryData.get("marketingPreference").getAsBoolean();
            boolean otherOptions = countryData.get("otherOptions").getAsBoolean();
            countries.add(new Country(name, marketingPreference, otherOptions));
        }

        return countries;
    }

    public Country getCountryDataByName(String name) {
        List<Country> countries = getCountries();
        for (Country country : countries) {
            if (country.getName().equals(name)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Country not found: " + name);
    }

    public Country getRandomCountryData() {
        List<Country> countries = getCountries();
        int randomIndex = random.nextInt(countries.size());
        return countries.get(randomIndex);
    }
}