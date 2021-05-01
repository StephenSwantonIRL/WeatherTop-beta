package models;

import java.util.ArrayList;
import java.util.List;

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
}