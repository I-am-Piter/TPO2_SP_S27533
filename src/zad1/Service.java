/**
 *
 *  @author Sułek Piotr S27533
 *
 */

package zad1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.http.HttpRequest;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {
    private String countryName;
    private String countryCode;
    private Locale country;

    public Service(String countryName) {
        this.countryName = countryName.toLowerCase();
        country = new Locale("",countryName);
        this.countryCode = country.getCountry();
        System.out.println(countryCode);
    }

    public String getWeather(String city) {
        String lat;
        String lon;
        String apiKey = "6ae983073ffef0266227e60865d98dd7";
        String url = "http://api.openweathermap.org/geo/1.0/direct?q="+city+","+country.getCountry()+"&appid="+apiKey;
        StringBuilder info = new StringBuilder();
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();

            if(response != 200){
                System.out.println("nie działa");
            }else{
                info = new StringBuilder();
                Scanner scanner = new Scanner(url1.openStream());

                while(scanner.hasNext()){
                    info.append(scanner.nextLine());
                }

                scanner.close();

                System.out.println(info);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Pattern latLon = Pattern.compile("\"lat\":(\\d+.\\d{7}),\"lon\":((\\d+.\\d{7}))");
        Matcher matcher = latLon.matcher(info);
        System.out.println(matcher.find());
        matcher.group();
        lat = matcher.group(1);
        lon = matcher.group(2);




        return city;
    }

    public Double getRateFor(String currency) {
        return null;
    }

    public Double getNBPRate() {
        return null;
    }
}
