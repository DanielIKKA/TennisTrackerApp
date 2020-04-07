package com.android.tennistrackerapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class Player {

    @DatabaseField(generatedId = true, canBeNull = false)
    private long id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField
    private int rank;
    @DatabaseField
    private String picture;

    // ----------------
    // CONSTRUCTOR
    // ----------------

    public Player() {}
    public Player(String name, int rank, String picture) {
        this.name = name;
        this.rank = rank;
        this.picture = picture;
    }

    // ----------------
    // GETTERS
    // ----------------
    public long getId() { return id; }
    public String getName() { return name; }
    public int getRank() { return rank; }
    public String getPicture() { return picture; }

    // ----------------
    // SETTERS
    // ----------------
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRank(int rank) { this.rank = rank; }
    public void setPicture(String picture) { this.picture = picture; }
}
