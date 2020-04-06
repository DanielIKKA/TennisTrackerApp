package com.android.tennistrackerapp.model.dao;


import com.android.tennistrackerapp.model.Player;

import java.util.ArrayList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDAO {

    @Query("SELECT * FROM player")
    ArrayList<Player> getAll();

    @Query("SELECT * FROM player WHERE id = :userId")
    ArrayList<Player> getById(long userId);

    @Insert
    long createOne(Player player);

    @Insert
    void insertAll(Player... players);

    @Update
    long update(Player player);

    @Delete
    void delete(Player player);
}
