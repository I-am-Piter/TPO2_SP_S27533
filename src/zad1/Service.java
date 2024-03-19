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
        this.countryName = countryName;
        country = new Locale("",getCountryCode(countryName));
         this.countryCode = country.getCountry();
    }
    public static String getCountryCode(String countryName) {
        String[] countryCodes = Locale.getISOCountries();
        Locale tmp = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return countryCode;
            }
        }
        Locale.setDefault(tmp);
        return Locale.getDefault().getCountry(); //w przypadku nieznalezienia kraju (mało prawdopodobne)
    }

    public String getWeather(String city) {
        String apiKey = "6ae983073ffef0266227e60865d98dd7";
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+","+countryCode+"&appid="+apiKey+"&units=metric";

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

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return info.toString();
    }

    public Double getRateFor(String currency) {
        NumberFormat currencyCode = NumberFormat.getCurrencyInstance(country);
        String url = "https://api.currencyapi.com/v3/latest?apikey=cur_live_9wH1PCm7aM57EAzJzMKlbQoD68UOk7fE7Ald4qnV&currencies="+currency+"&base_currency="+currencyCode.getCurrency();

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

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String toReturn = "";
        JSONObject json = null;
        try {
            json = new JSONObject(info.toString());
            toReturn = json.getJSONObject("data").getJSONObject(currency).get("value").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return Double.parseDouble(toReturn);
    }

    public Double getNBPRate() {
        if (countryCode == "PL"){
            return 1.0;
        }

        NumberFormat currencyCode = NumberFormat.getCurrencyInstance(country);
        String[] urls = {"http://api.nbp.pl/api/exchangerates/rates/a/"+currencyCode.getCurrency().toString().toLowerCase()+"/",
                "http://api.nbp.pl/api/exchangerates/rates/b/"+currencyCode.getCurrency().toString().toLowerCase()+"/",
                "http://api.nbp.pl/api/exchangerates/rates/c/"+currencyCode.getCurrency().toString().toLowerCase()+"/"};

        StringBuilder info = new StringBuilder();
        for (String url:urls) {
            try {
                URL url1 = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int response = conn.getResponseCode();

                if (response != 200) {
                    System.out.println("nie działa");
                } else {
                    info = new StringBuilder();
                    Scanner scanner = new Scanner(url1.openStream());

                    while (scanner.hasNext()) {
                        info.append(scanner.nextLine());
                    }

                    scanner.close();

                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!info.isEmpty()) {
                break;
            }
        }

        double toReturn = 0;
        JSONObject json = null;
        try {
            json = new JSONObject(info.toString());
            toReturn = json.getJSONArray("rates").getJSONObject(0).getDouble("mid");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return toReturn;
    }
}
