package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Reading extends Model {
    public int code ;
    public float temperature ;
    public float windSpeed ;
    public float windDirection ;
    public int pressure ;
    public String timestamp ;

    public Reading(int code, float temperature, float windSpeed, float windDirection, int pressure)
    {
        this.code = code;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dateFormatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = timeNow.format(dateFormatPattern);



    }
}
