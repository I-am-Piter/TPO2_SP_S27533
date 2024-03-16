/**
 *
 *  @author Sułek Piotr S27533
 *
 */

package zad1;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.http.HttpRequest;
import java.text.NumberFormat;
import java.util.Currency;
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
        String lat = null;
        String lon = null;
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
        JSONObject jobj = null;
        try {
            jobj = new JSONObject(info.toString().replaceAll("[\\[\\]]",""));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            lat = jobj.get("lat").toString();
            lon = jobj.get("lon").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+apiKey;

        info.delete(0,info.length());
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

        try {
            JSONObject pogoda = new JSONObject(info.toString());
            pogoda = new JSONObject(pogoda.get("weather").toString().replaceAll("[\\[\\]]",""));
            System.out.println(pogoda.get("main"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return info.toString();
    }

    public Double getRateFor(String currency) {
        NumberFormat currencyCode = NumberFormat.getCurrencyInstance(country);
        System.out.println(currencyCode.getCurrency());
        String url = "https://api.currencyapi.com/v3/latest?apikey=cur_live_9wH1PCm7aM57EAzJzMKlbQoD68UOk7fE7Ald4qnV&currencies=EUR&base_currency=PLN";
        return null;
    }

    public Double getNBPRate() {
        return null;
    }
}
