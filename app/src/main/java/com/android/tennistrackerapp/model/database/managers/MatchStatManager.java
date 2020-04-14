package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.MatchStat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;


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

    // --------------
    // QUERIES
    // --------------
    public MatchStat getOneById(int id) {
        try {
            return this.dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MatchStat();
        }
    }

    public ArrayList<MatchStat> getAll() {
        try {
            return (ArrayList<MatchStat>) this.dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int createOne(MatchStat newMatch) {
        try {
            this.dao.create(newMatch);
            return newMatch.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }

    public boolean deleteOneById(Integer id) {
        try {
            this.dao.deleteById(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete(ArrayList<MatchStat> matches) {
        try {
            dao.delete(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MatchStat> getAllMatchWithPlayerId(int plyerId) {

        QueryBuilder<MatchStat, Integer> builder = dao.queryBuilder();
        PreparedQuery<MatchStat> preparedQuery;
        try {
            preparedQuery = builder.where().eq(MatchStat.PLAYER_ID, plyerId).prepare();
            return (ArrayList<MatchStat>) dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<MatchStat> getAllMatchWithMatchId(int matchId) {

        QueryBuilder<MatchStat, Integer> builder = dao.queryBuilder();
        PreparedQuery<MatchStat> preparedQuery;
        try {
            preparedQuery = builder.where().eq(MatchStat.MATCH_ID, matchId).prepare();
            return (ArrayList<MatchStat>) dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
