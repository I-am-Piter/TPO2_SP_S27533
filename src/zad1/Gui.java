package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Gui{

    JFrame ramka = new JFrame();
    Panel panelPogodowy;
    Panel panelWalutowy;
    Panel panelNBP;
    Panel wikipedia;
    JButton zmianaKrajuIMiasta;
    Service s;

    public Gui(String weatherJson, Double rate1, Double rate2) {
        panelPogodowy = new Panel();
        panelWalutowy = new Panel();
        panelNBP = new Panel();
        wikipedia = new Panel();
        zmianaKrajuIMiasta = new JButton("zmiana kraju i miasta");


        ramka.setVisible(true);

        JSONObject jobject = null;
        String weatherIfno = "";
        String miasto = "";
        String temperature = "";
        try {
            jobject = new JSONObject(weatherJson);
            weatherIfno = (String) jobject.getJSONArray("weather").getJSONObject(0).get("main");
            temperature = jobject.getJSONObject("main").get("temp").toString();
            miasto = (String) jobject.get("name");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPogodowy.add(new JLabel("<html>weather: <br>"+weatherIfno+"<br>"+temperature+" C</html>"));
        panelWalutowy.add(new JLabel("Currency rate: \n"+String.valueOf(rate1)));
        panelNBP.add(new JLabel("NBP Rate: \n" + String.valueOf(rate2)));
        String[] krajMiasto = new String[3];

        final String miastoFinal = miasto;
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            WebView browser = new WebView();
            WebEngine webEngine = browser.getEngine();

            webEngine.load("https://en.wikipedia.org/wiki/"+miastoFinal);
            Scene scene = new Scene(browser);
            jfxPanel.setScene(scene);
        });

        zmianaKrajuIMiasta.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                krajMiasto[0] = JOptionPane.showInputDialog(ramka,
                        "Podaj (kraj,miasto,kodWaluty) po angielsku", null);

                String kraj;
                String miasto;
                String kodWaluty;
                kraj = krajMiasto[0].split(",")[0];
                miasto = krajMiasto[0].split(",")[1];
                kodWaluty = krajMiasto[0].split(",")[2];

                kraj = kraj.replace(" ","");
                miasto = miasto.replace(" ","");
                kodWaluty = kodWaluty.replace(" ","");

                Service s = new Service(kraj);

                JSONObject jobject = null;
                String weatherIfno = s.getWeather(miasto);
                String temperature = "";
                try {
                    jobject = new JSONObject(s.getWeather(miasto));
                    weatherIfno = (String) jobject.getJSONArray("weather").getJSONObject(0).get("main");
                    temperature = jobject.getJSONObject("main").get("temp").toString();
                } catch (JSONException e2) {
                    throw new RuntimeException(e2);
                }
                final String miastoFinal = miasto;
                Platform.runLater(() -> {
                    WebView browser = new WebView();
                    WebEngine webEngine = browser.getEngine();

                    webEngine.load("https://en.wikipedia.org/wiki/"+miastoFinal);
                    Scene scene = new Scene(browser);
                    jfxPanel.setScene(scene);
                });
                panelPogodowy.remove(0);
                panelWalutowy.remove(0);
                panelNBP.remove(0);
                panelNBP.revalidate();
                panelWalutowy.revalidate();
                panelPogodowy.revalidate();
                jfxPanel.revalidate();

                panelPogodowy.add(new JLabel("<html>weather: <br>"+weatherIfno+"<br>"+temperature+" C</html>"));
                panelWalutowy.add(new JLabel("Currency rate: \n"+String.valueOf(s.getRateFor(kodWaluty))));
                panelNBP.add(new JLabel("NBP Rate: \n" + String.valueOf(s.getNBPRate())));
                ramka.revalidate();

            }
        });




        this.ramka.setSize(1000,1000);
        this.ramka.setVisible(true);
        this.ramka.setLayout(new BorderLayout());
        this.ramka.add(panelNBP,BorderLayout.LINE_START);
        this.ramka.add(panelWalutowy,BorderLayout.NORTH);
        this.ramka.add(panelPogodowy,BorderLayout.LINE_END);
        this.ramka.add(zmianaKrajuIMiasta,BorderLayout.SOUTH);
        this.ramka.add(jfxPanel,BorderLayout.CENTER);


    }
}
