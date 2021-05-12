package controllers;

import models.Reading;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;
import models.Member;
import models.Station;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Station> stations1 = member.stations;
    List<Station> alphabeticalStations = member.renderAlphabetically(stations1);
    List<Station>stations = alphabeticalStations;

    Logger.info("station array = " + stations);
    render ("dashboard.html", stations);
  }
  public static void addStation (String name, float latitude, float longitude)
  {
    Logger.info("Adding a Station");
    Member member = Accounts.getLoggedInMember();
    List<Reading> readings = new ArrayList<Reading>();
    Station station = new Station(name,latitude,longitude, readings);
    member.stations.add(station);
    member.save();
    redirect ("/dashboard");
  }
  public static void deleteStation (Long id)
  {
    Logger.info("Deleting a Station");
    Member member = Accounts.getLoggedInMember();
    Station station = Station.findById(id);
    member.stations.remove(station);
    member.save();
    station.delete();
    redirect ("/dashboard");
  }


}
