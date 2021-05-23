package models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import play.db.jpa.Model;

@Entity
public class Station extends Model {
    public String name;
    public float latitude;
    public float longitude;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name, float latitude, float longitude, List<Reading> readings) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.readings = readings;

    }

    public String readingMaxMin(String type, String property) {
        ArrayList<Float> tempList = new ArrayList<Float>();
        ArrayList<Float> windSpeedList = new ArrayList<Float>();
        ArrayList<Integer> pressureList = new ArrayList<Integer>();
        for (Reading reading : readings) {
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
        if (type.equals("min")) {
            valueTemp = Collections.min(tempList);
            valueWindSpeed = Collections.min(windSpeedList);
            valuePressure = Collections.min(pressureList);
        }
        String value = "";
        if (property.equals("temp")) {
            value = String.format("%.1f", valueTemp);
        }
        if (property.equals("wind")) {
            value = String.format("%.1f", valueWindSpeed);
        }
        if (property.equals("pressure")) {
            value = Integer.toString(valuePressure);
        }
        return value;
    }

    public HashMap<Integer, Long> getLatestThreeReadings() {
        HashMap<Integer, Long> latestThree = new HashMap<Integer,Long>();
        DateTimeFormatter dateFormatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeToSort;
        LocalDateTime[] sortedTimes = new LocalDateTime[3];
        Long[] sortedDbID = new Long[3];

        if (readings.size() == 1) {
            latestThree.put(1,readings.get(0).id);
            return latestThree;
        }
        if (readings.size() == 2) {
            if(LocalDateTime.parse(readings.get(0).timestamp, dateFormatPattern).isAfter(LocalDateTime.parse(readings.get(1).timestamp, dateFormatPattern))) {
                latestThree.put(1, readings.get(0).id);
                latestThree.put(2, readings.get(1).id);
            } else {
                latestThree.put(1, readings.get(1).id);
                latestThree.put(2, readings.get(0).id);
            }
            return latestThree;
        }
        for (Reading reading : readings) {
            timeToSort = LocalDateTime.parse(reading.timestamp, dateFormatPattern);
            if (latestThree.size() == 0) {
                sortedDbID[0] = reading.id;
                sortedTimes[0] = timeToSort;
                latestThree.put(1,sortedDbID[0]);

            } else if (latestThree.size()==1) {
                if (timeToSort.isAfter(sortedTimes[0])) {
                    sortedDbID[1] = sortedDbID[0];
                    sortedDbID[0] = reading.id;
                    sortedTimes[1] = sortedTimes[0];
                    sortedTimes[0] = timeToSort;
                } else {
                    sortedDbID[1] = reading.id;
                    sortedTimes[1] = timeToSort;
                    latestThree.put(2,sortedDbID[1]);
                }
                latestThree.put(2,sortedDbID[1]);
            } else if (latestThree.size()==2 || timeToSort.isAfter(sortedTimes[2])) {
                if (timeToSort.isAfter(sortedTimes[0])) {
                    sortedDbID[2] = sortedDbID[1];
                    sortedDbID[1] = sortedDbID[0];
                    sortedDbID[0] = reading.id;
                    sortedTimes[2] = sortedTimes[1];
                    sortedTimes[1] = sortedTimes[0];
                    sortedTimes[0] = timeToSort;
                } else if(timeToSort.isAfter(sortedTimes[1]) && timeToSort.isBefore(sortedTimes[0])) {
                    sortedDbID[2] = sortedDbID[1];
                    sortedDbID[1] = reading.id;
                    sortedTimes[2] = sortedTimes[1];
                    sortedTimes[1] = timeToSort;
                    } else {
                    sortedDbID[2] = reading.id;
                    sortedTimes[2] = timeToSort;
                }
                if(latestThree.size()==2) {
                    latestThree.put(3,sortedDbID[2]);
                }
                } else{

                    }

                }
        latestThree.replace(1,sortedDbID[0]);
        latestThree.replace(2,sortedDbID[1]);
        latestThree.replace(3,sortedDbID[2]);
        return latestThree;
            }
    public static String determineTrend(float reading1, float reading2, float reading3) {
        String trend = "";
        if (reading1 > reading2 && reading2 > reading3) {
            trend = "Rising";
        } else if (reading3 > reading2 && reading2 > reading1) {
            trend = "Falling";
        } else {
            trend = "Steady";
        }
        return trend;
    }

    public String outputTrend(String property){
        String trend = "";
        if(readings.size()>2) {
            HashMap<Integer, Long> latestReadings = getLatestThreeReadings();

            Reading reading1 = Reading.findById(latestReadings.get(1));
            Reading reading2 = Reading.findById(latestReadings.get(2));
            Reading reading3 = Reading.findById(latestReadings.get(3));
            if (property == "temp") {
                trend = Station.determineTrend(reading1.temperature, reading2.temperature, reading3.temperature);
            }
            if (property == "wind") {
                trend = Station.determineTrend(reading1.windSpeed, reading2.windSpeed, reading3.windSpeed);
            }
            if (property == "pressure") {
                trend = Station.determineTrend(reading1.pressure, reading2.pressure, reading3.pressure);
            }

        }
        return trend;
    }
    public String trendIcon(String type){
        String trendIcon = "";
        if (type == "Falling") {
            trendIcon = "down";
        }
        if (type == "Rising") {
            trendIcon = "up";
        }
        return trendIcon;
    }

    public Reading findReadingById(Long id) {

        Reading reading = Reading.findById(id);
        return reading;
    }

    public String getName(){
        return this.name;
    }

    }

