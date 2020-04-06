package com.android.tennistrackerapp.model.dao;


import com.android.tennistrackerapp.model.MatchStat;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MatchStatDAO {

    @Query("SELECT * FROM statistics")
    LiveData<List<MatchStat>> getAll();

    @Query("SELECT * FROM statistics WHERE id = :statisticId")
    LiveData<List<MatchStat>> getById(long statisticId);

    @Insert
    long createOne(MatchStat stats);

    @Insert
    void insertAll(MatchStat... statss);

    @Update
    int update(MatchStat stats);

    @Delete
    void delete(MatchStat stats);
}
