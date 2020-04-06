package com.android.tennistrackerapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int id;
    private String name;
    private int rank;
    private String picture;

    // ----------------
    // CONSTRUCTOR
    // ----------------


    public User(String name, int rank, String picture) {
        this.name = name;
        this.rank = rank;
        this.picture = picture;
    }

    // ----------------
    // GETTERS
    // ----------------
    public int getId() { return id; }
    public String getName() { return name; }
    public int getRank() { return rank; }
    public String getPicture() { return picture; }

    // ----------------
    // SETTERS
    // ----------------
    public void setName(String name) { this.name = name; }
    public void setRank(int rank) { this.rank = rank; }
    public void setPicture(String picture) { this.picture = picture; }
}
