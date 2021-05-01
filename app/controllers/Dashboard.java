package controllers;

import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;
import models.Member;

public class Dashboard extends Controller
{
  public static void index() {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    /*List<Station> stations = member.stations;*/
    render ("dashboard.html");
  }
}
