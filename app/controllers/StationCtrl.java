package controllers;

import models.Member;
import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class StationCtrl extends Controller
{
  public static void index(Long id)
  {
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    List<Station> stations = member.stations;
    List<Long> stationIDs = new ArrayList<Long>();
    for (int i =0; i<member.stations.size(); i++) {
        stationIDs.add(member.stations.get(i).id);
    }

    if (stationIDs.contains(id)) {
      Logger.info("Station id = " + id);
      render("station.html", station);
    } else {
      Accounts.login();
    }
    }


  public static void deletereading(Long id, Long readingid)
  {
    Station station = Station.findById(id);
    Reading reading = Reading.findById(readingid);
    Logger.info ("Removing Reading No. " + readingid );
    station.readings.remove(reading);
    station.save();
    reading.delete();
    render("station.html", station);
  }

  public static void addReading(Long id, int code, float temperature, float windSpeed, float windDirection, int pressure)
  {
    Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure);
    Station station = Station.findById(id);
    station.readings.add(reading);
    station.save();
    redirect ("/station/" + id);
  }
}
