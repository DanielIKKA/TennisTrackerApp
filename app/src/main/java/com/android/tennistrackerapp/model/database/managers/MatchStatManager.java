package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.MatchStat;
import com.j256.ormlite.dao.Dao;


public class MatchStatManager {
    private Dao<MatchStat, Integer> dao;

    // --------------
    // CONSTRUCTOR
    // --------------
    public MatchStatManager(Dao<MatchStat, Integer> dao) {
        this.dao = dao;
    }

    // --------------
    // QUERIES
    // --------------

}
