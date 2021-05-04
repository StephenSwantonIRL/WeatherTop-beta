package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model
{
    public String name;
    public float latitude ;
    public float longitude;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, float latitude, float longitude, List<Reading> readings)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.readings = readings;

    }

    public String readingMaxMin(String type, String property){
        ArrayList<Float> tempList = new ArrayList<Float>();
        ArrayList<Float> windSpeedList = new ArrayList<Float>();
        ArrayList<Integer> pressureList = new ArrayList<Integer>();
        for (Reading reading:readings) {
            tempList.add(reading.temperature);
            pressureList.add(reading.pressure);
            windSpeedList.add(reading.windSpeed);
        }

        Float valueTemp = 0.0f;
        Float valueWindSpeed = 0.0f;
        int valuePressure = 0;
        if (type.equals("max")) {
            valueTemp = Collections.max(tempList);
            valueWindSpeed = Collections.max(windSpeedList);
            valuePressure = Collections.max(pressureList);
        }
        if (type.equals("min")){
            valueTemp = Collections.min(tempList);
            valueWindSpeed = Collections.min(windSpeedList);
            valuePressure = Collections.min(pressureList);
        }
        String value ="";
        if (property.equals("temp")) {
            value = String.format("%.2f", valueTemp);
        }
        if (property.equals("wind")){
            value = String.format("%.2f", valueWindSpeed);
        }
        if (property.equals("pressure")){
                value = Integer.toString(valuePressure);
        }
        return value;
    }
}