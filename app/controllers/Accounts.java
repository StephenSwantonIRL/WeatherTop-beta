package controllers;

import models.Member;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller
{
  public static void signup()
  {
    render("signup.html");
  }

  public static void editProfile()
  {
    Member member = getLoggedInMember();
    String notify = "Edit your details using this form.";
    render("editprofile.html", member, notify);
  }

  public static void updateProfile(String firstname, String lastname, String email, String password)
  {
    Member member = getLoggedInMember();
    member.firstname = firstname;
    member.lastname = lastname;
    member.email = email;
    member.password = password;
    member.save();
    String notify = "Your updated details have been saved!";
    render("editprofile.html", member, notify);
  }

  public static void login()
  {
    render("login.html");
  }

  public static void register(String firstname, String lastname, String email, String password)
  {
    Logger.info("Registering new user " + email);
    Member member = new Member(firstname, lastname, email, password);
    member.save();
    redirect("/dashboard");
  }

  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);

    Member member = Member.findByEmail(email);
    if ((member != null) && (member.checkPassword(password) == true)) {
      Logger.info("Authentication successful");
      session.put("logged_in_Memberid", member.id);
      redirect ("/dashboard");
    } else {
      Logger.info("Authentication failed");
      String notify = "Your details are incorrect. Please try again.";
      render("login.html", notify);
    }
  }

  public static void logout()
  {
    session.clear();
    redirect ("/");
  }

  public static Member getLoggedInMember()
  {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    } else {
      login();
    }
    return member;
  }
}