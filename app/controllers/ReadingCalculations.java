package controllers;

import models.Station;
import play.Logger;
import play.mvc.Controller;
import java.util.HashMap;

public class ReadingCalculations extends Controller
{

  public static double toFahrenheit(double degreesCelsius){
    double degreesFahrenheit = (degreesCelsius * 9/5)+32;
    return degreesFahrenheit;
  }

  public static String weatherLabel(int code) {

    String weatherLabel ="";
    switch (code) {
      case (100):
        weatherLabel = "Clear";
        break;
      case (200):
        weatherLabel = "Partial clouds";
        break;
      case (300):
        weatherLabel = "Cloudy";
        break;
      case (400):
        weatherLabel = "Light Showers";
        break;
      case (500):
        weatherLabel = "Heavy Showers";
        break;
      case (600):
        weatherLabel = "Rain";
        break;
      case (700):
        weatherLabel = "Snow";
        break;
      case (800):
        weatherLabel = "Thunder";
        break;
      default:
        weatherLabel = "error - Invalid Weather Code";
    }
    return weatherLabel;
  }

  public static double calculateWindChill( double temperature, double windSpeed){
    double windChill = 13.12 + 0.6215*temperature - 11.37 *(Math.pow((float)windSpeed,0.16)) + 0.3965*temperature*(Math.pow((float)windSpeed,0.16));
    return windChill;
  }

  public static HashMap<Integer,String> convertToBeaufort(float windSpeed){
    int beaufort = 0;
    String beaufortLabel ="";

    if (windSpeed<1){
      beaufort =0;
      beaufortLabel = "Calm";}

    if (windSpeed>=1 && windSpeed <=5){
      beaufort =1;
      beaufortLabel = "Light Air";}

    if (windSpeed>=6 && windSpeed <=11){
      beaufort =2;
      beaufortLabel = "Light Breeze";}

    if (windSpeed>=12 && windSpeed <=19){
      beaufort =3;
      beaufortLabel = "Gentle Breeze";}

    if (windSpeed>=20 && windSpeed <=28){
      beaufort =4;
      beaufortLabel = "Moderate Breeze";}

    if (windSpeed>=29 && windSpeed <=38){
      beaufort =5;
      beaufortLabel = "Fresh Breeze";}

    if (windSpeed>=39 && windSpeed <=49){
      beaufort =6;
      beaufortLabel = "Strong Breeze";}

    if (windSpeed>=50 && windSpeed <=61){
      beaufort =7;
      beaufortLabel = "Near Gale";}

    if (windSpeed>=62 && windSpeed <=74){
      beaufort =8;
      beaufortLabel = "Gale";}

    if (windSpeed>=75 && windSpeed <=88){
      beaufort =9;
      beaufortLabel = "Severe Gale";}

    if (windSpeed>=89 && windSpeed <=102){
      beaufort =10;
      beaufortLabel = "Strong Storm";}

    if (windSpeed>=103 && windSpeed <=117){
      beaufort =11;
      beaufortLabel = "Violent Storm";
    }
    HashMap<Integer, String> beaufortValues = new HashMap<Integer, String>();
    beaufortValues.put(beaufort, beaufortLabel);
    return beaufortValues;
  }

  public static String windDirectionLabel(double windDirection){

    String windDirectionLabel ="";
    if ((windDirection>360 )|| (windDirection<0)){
      windDirectionLabel ="error - Invalid wind direction value";
    }
    if ((windDirection>=348.75 && windDirection<=360 )|| (windDirection>=0 && windDirection <11.25)){
      windDirectionLabel ="North";
    }
    if (windDirection>=11.25 && windDirection <33.75){
      windDirectionLabel ="North North East";
    }
    if (windDirection>=33.75 && windDirection <56.25){
      windDirectionLabel ="North East";
    }
    if (windDirection>=56.25 && windDirection <78.75){
      windDirectionLabel ="East North East";
    }
    if (windDirection>=78.75 && windDirection <101.25){
      windDirectionLabel ="East";
    }
    if (windDirection>=101.25 && windDirection <123.75){
      windDirectionLabel ="East South East";
    }
    if (windDirection>=123.75 && windDirection <146.25){
      windDirectionLabel ="South East";
    }
    if (windDirection>=146.25 && windDirection <168.75){
      windDirectionLabel ="South South East";
    }
    if (windDirection>=168.75 && windDirection <191.25){
      windDirectionLabel ="South";
    }
    if (windDirection>=191.25 && windDirection <213.75){
      windDirectionLabel ="South South West";
    }
    if (windDirection>=213.75 && windDirection <236.25){
      windDirectionLabel ="South West";
    }
    if (windDirection>=236.25 && windDirection <258.75){
      windDirectionLabel ="West South West";
    }
    if (windDirection>=258.75 && windDirection <281.25){
      windDirectionLabel ="West";
    }
    if (windDirection>=281.25 && windDirection <303.75){
      windDirectionLabel ="West North West";
    }
    if (windDirection>=303.75 && windDirection <326.25){
      windDirectionLabel ="North West";
    }
    if (windDirection>=326.25 && windDirection <348.75){
      windDirectionLabel ="North North West";
    }
    return windDirectionLabel;
  }

  public static String isTrend(double reading1, double reading2, double reading3){
    String trend = "";
    if (reading1 > reading2 && reading2 > reading3) {
      trend = "Rising";
    } else if ( reading3> reading2 && reading2 > reading1) {
      trend = "Falling";
    } else {
      trend = "Steady";
    }
    return trend;
  }

  public static int findIDofMostRecent(Station station){
    int mostRecentReadingId = 0;
    return mostRecentReadingId;
  }
}