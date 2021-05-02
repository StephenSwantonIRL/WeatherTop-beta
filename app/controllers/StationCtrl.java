package controllers;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

public class StationCtrl extends Controller
{
  public static void index(Long id)
  {
    Station station = Station.findById(id);
    Logger.info ("Station id = " + id);
    render("station.html", station);
  }

/*  public static void deletesong (Long id, Long songid)
  {
    Playlist playlist = Playlist.findById(id);
    Song song = Song.findById(songid);
    Logger.info ("Removing" + song.title);
    playlist.songs.remove(song);
    playlist.save();
    song.delete();
    render("playlist.html", playlist);
  }*/
  public static void addReading(Long id, int code, float temperature, float windSpeed, float windDirection, int pressure)
  {
    Reading reading = new Reading(code, temperature, windSpeed, windDirection, pressure);
    Station station = Station.findById(id);
    station.readings.add(reading);
    station.save();
    redirect ("/station/" + id);
  }
}
