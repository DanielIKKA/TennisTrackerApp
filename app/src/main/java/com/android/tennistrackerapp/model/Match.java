package com.android.tennistrackerapp.model;

import java.io.Serializable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "matches")
public class Match {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ForeignKey(entity = Player.class, parentColumns = "id", childColumns = "winner_player_id")
    private long winner_player_id;
    @ForeignKey(entity = Player.class, parentColumns = "id", childColumns = "looser_player_id")
    private long looser_player_id;
    private String date;
    @Embedded
    private Location location;

    // ----------------
    // DEFAULT VALUES
    // ----------------
    private static final long DEFAULT_ID_PLAYER_1 = 1;
    private static final long DEFAULT_ID_PLAYER_2 = 2;
    private static final String DEFAULT_DATE = "06/04/2020";
    private static final Location DEFAULT_LOCATION = Location.getDefaultInstance();

    // ----------------
    // CONSTRUCTORS
    // ----------------
    public static Match getInstance() {
        return new Match(DEFAULT_ID_PLAYER_1, DEFAULT_ID_PLAYER_2, DEFAULT_DATE, DEFAULT_LOCATION);
    }

    public Match(long winner_player_id, long looser_player_id, String date, Location location) {
        this.winner_player_id = winner_player_id;
        this.looser_player_id = looser_player_id;
        this.date = date;
        this.location = location;
    }

    // ----------------
    // NESTED CLASSES
    // ----------------
    public static class Location implements Serializable {

        private static final double DEFAULT_LONG = 2.3488;
        private static final double DEFAULT_LAT = 48.8534;

        private double longitude;
        private double latitude;


        private static final Location DEFAULT_INSTANCE = new Location(DEFAULT_LONG, DEFAULT_LAT);

        public Location(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        static Location getDefaultInstance() { return DEFAULT_INSTANCE; }

        // ----------------
        // GETTERS
        // ----------------
        public double getLongitude() { return longitude; }
        public double getLatitude() { return latitude; }

        // ----------------
        // SETTERS
        // ----------------
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }

        // ----------------
        // DISPLAY
        // ----------------

        public String toString() {
            return "Location{" +
                    "long=" + longitude +
                    ", lat=" + latitude +
                    '}';
        }
    }

    // ----------------
    // GETTERS
    // ----------------
    public long getId() { return id; }
    public long getWinner_player_id() { return winner_player_id; }
    public long getLooser_player_id() { return looser_player_id; }
    public String getDate() { return date; }
    public Location getLocation() { return location; }


    // ----------------
    // SETTERS
    // ----------------
    public void setId(long id) { this.id = id; }
    public void setWinner_player_id(long winner_player_id) { this.winner_player_id = winner_player_id; }
    public void setLooser_player_id(long looser_player_id) { this.looser_player_id = looser_player_id; }
    public void setDate(String date) { this.date = date; }
    public void setLocation(Location location) { this.location = location; }
}