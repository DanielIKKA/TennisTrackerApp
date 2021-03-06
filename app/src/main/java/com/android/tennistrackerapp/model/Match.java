package com.android.tennistrackerapp.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "matches")
public class Match {

    public static final String WINNER_FIELD_NAME = "id_winner_player";
    public static final String LOOSER_FIELD_NAME = "id_looser_player";

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(foreign = true, canBeNull = false, columnName = WINNER_FIELD_NAME, foreignAutoRefresh = true)
    private Player winner;
    @DatabaseField(foreign = true, canBeNull = false, columnName = LOOSER_FIELD_NAME, foreignAutoRefresh = true)
    private Player looser;
    @DatabaseField
    private Date date;
    @DatabaseField(dataType = DataType.SERIALIZABLE, useGetSet = true)
    private Location location;
    @DatabaseField(dataType = DataType.BYTE_ARRAY) private byte[] image;

    // ----------------
    // CONSTRUCTORS
    // ----------------
    public Match(){}
    public Match(Player winner, Player looser, Date date, Location location) {
        this.winner = winner;
        this.looser = looser;
        this.date = date;
        this.location = location;
    }

    // ----------------
    // NESTED CLASSES
    // ----------------
    public static class Location implements Serializable {

        private static final double DEFAULT_LONG = 2.3488;
        private static final double DEFAULT_LAT = 48.8534;

        private Double longitude;
        private Double latitude;

        private static final Location DEFAULT_INSTANCE = new Location(DEFAULT_LONG, DEFAULT_LAT);

        public Location(Double longitude, Double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        static Location getDefaultInstance() { return DEFAULT_INSTANCE; }

        // ----------------
        // GETTERS
        // ----------------
        public Double getLongitude() { return longitude; }
        public Double getLatitude() { return latitude; }

        // ----------------
        // SETTERS
        // ----------------
        public void setLongitude(double longitude) { this.longitude = longitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }

        // ----------------
        // DISPLAY
        // ----------------

        public String toString() {

            return this.equals(null) ? "N/A" : String.format("({0} ; {1})", this.latitude.toString(), this.longitude.toString());
        }
    }

    // ----------------
    // GETTERS
    // ----------------
    public int getId() { return id; }
    public Player getWinner() { return winner; }
    public Player getLooser() { return looser; }
    public Date getDate() { return date; }
    public Location getLocation() { return location; }
    public byte[] getImage() {
        return image;
    }

    // ----------------
    // SETTERS
    // ----------------
    public void setId(int id) { this.id = id; }
    public void setWinner(Player winner) { this.winner = winner; }
    public void setLooser(Player looser) { this.looser = looser; }
    public void setDate(Date date) { this.date = date; }
    public void setLocation(Location location) { this.location = location; }

    public void setImage(byte[] image) {
        this.image = image;
    }
}