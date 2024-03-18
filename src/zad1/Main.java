/**
 *
 *  @author Sułek Piotr S27533
 *
 */

package zad1;


import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();

    System.out.println(weatherJson);
    System.out.println(rate1);
    System.out.println(rate2);

//    SwingUtilities.invokeLater(new Runnable() {
//      @Override
//      public void run() {
//        Gui gui = new Gui(weatherJson,rate1,rate2);
//      }
//    });
    // ...
    // część uruchamiająca GUI
  }
}
