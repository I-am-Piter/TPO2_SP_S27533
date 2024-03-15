/**
 *
 *  @author Sułek Piotr S27533
 *
 */

package zad1;


import java.util.Locale;

public class Service {
    private String countryName;
    private Locale country = new Locale(countryName);

    public Service(String countryName) {
        this.countryName = countryName;
    }

    public String getWeather(String town) {
        return town;
    }

    public Double getRateFor(String currency) {
        return null;
    }

    public Double getNBPRate() {
        return null;
    }
}
