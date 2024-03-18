package zad1;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.JComponent;
import java.awt.*;

public class Gui{
    JFrame ramka = new JFrame();
    Panel panelPogodowy;
    Panel panelWalutowy;
    Panel panelNBP;
    Panel wikipedia;

    public Gui(String weatherJson, Double rate1, Double rate2) {
        panelPogodowy = new Panel();
        panelWalutowy = new Panel();
        panelNBP = new Panel();
        wikipedia = new Panel();

        JSONObject jobject = null;
        String weatherIfno = "";
        try {
            jobject = new JSONObject(weatherJson);
            weatherIfno = (String) jobject.getJSONArray("weather").getJSONObject(0).get("main");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        this.ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelPogodowy.add(new JLabel("weather: \n"+weatherIfno));
        panelWalutowy.add(new JLabel("Currency rate: \n"+String.valueOf(rate1)));
        panelNBP.add(new JLabel("NBP Rate: \n" + String.valueOf(rate2)));



        this.ramka.setSize(1000,1000);
        this.ramka.setVisible(true);
        this.ramka.setLayout(new BorderLayout());
        this.ramka.add(panelNBP,BorderLayout.LINE_START);
        this.ramka.add(panelWalutowy,BorderLayout.NORTH);
        this.ramka.add(panelPogodowy,BorderLayout.LINE_END);
        this.ramka.add(wikipedia,BorderLayout.CENTER);

    }
}
