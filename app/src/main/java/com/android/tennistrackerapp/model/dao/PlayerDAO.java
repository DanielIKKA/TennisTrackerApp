package com.android.tennistrackerapp.model.dao;


import com.android.tennistrackerapp.model.Player;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlayerDAO {

    @Query("SELECT * FROM players")
    LiveData<List<Player>> getAll();

    @Query("SELECT * FROM players WHERE id = :userId")
    LiveData<List<Player>> getById(long userId);

    @Insert
    long createOne(Player player);

    @Insert
    void insertAll(Player... players);

    @Update
    int update(Player player);

    @Delete
    void delete(Player player);
}
