package com.android.tennistrackerapp.model.dao;


import com.android.tennistrackerapp.model.Match;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MatchDAO {

    @Query("SELECT * FROM matches")
    LiveData<List<Match>> getAll();

    @Query("SELECT * FROM matches WHERE id = :matchId")
    LiveData<List<Match>> getById(long matchId);

    @Insert
    long createOne(Match match);

    @Insert
    void insertAll(Match... matches);

    @Update
    int update(Match match);

    @Delete
    void delete(Match match);
}
