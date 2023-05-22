/**
 * File: Country.java
 * Author: Waruna
 * Created: 5/20/2023
 * Description: Class that represents a country.
 */
package objects;

public class Country {
    private final String name;
    private final boolean marketingPreference;
    private final boolean otherOptions;

    public Country(String name, boolean marketingPreference, boolean otherOptions) {
        this.name = name;
        this.marketingPreference = marketingPreference;
        this.otherOptions = otherOptions;
    }

    public String getName() {
        return name;
    }

    public boolean isMarketingPreference() {
        return marketingPreference;
    }

    public boolean isOtherOptions() {
        return otherOptions;
    }
}
