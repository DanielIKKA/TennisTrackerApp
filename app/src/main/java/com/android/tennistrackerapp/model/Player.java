package com.android.tennistrackerapp.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class Player {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField private int rank;
    @DatabaseField private int age;
    @DatabaseField(dataType = DataType.BYTE_ARRAY) private byte[] picture;

    // ----------------
    // CONSTRUCTOR
    // ----------------

    public Player() {}
    public Player(String name, int rank, int age) {
        this(name, rank, age, new byte[]{});
    }
    public Player(String name, int rank, int age, byte[] picture) {
        this.name = name;
        this.rank = rank;
        this.age = age;
        this.picture = picture;
    }

    // ----------------
    // GETTERS
    // ----------------
    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getRank() { return rank; }
    public int getAge() { return age; }
    public byte[] getPicture() { return picture; }

    // ----------------
    // SETTERS
    // ----------------
    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRank(int rank) { this.rank = rank; }
    public void setAge(int age) { this.age = age; }
    public void setPicture(byte[] picture) { this.picture = picture; }
}
