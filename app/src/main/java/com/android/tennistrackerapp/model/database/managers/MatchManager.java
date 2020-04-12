package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.Match;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchManager {
    private Dao<Match, Integer> dao;

    // --------------
    // CONSTRUCTOR
    // --------------
    public MatchManager(Dao<Match, Integer> dao) {
        this.dao = dao;
    }

    // --------------
    // QUERIES
    // --------------
    public List<Match> getAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Match>();
        }
    }

    public long createOne(Match newMatch) {
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

    public void delete(ArrayList<Match> matches) {
        try {
            dao.delete(matches);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Match> getAllMatchWith(int id) {

        QueryBuilder<Match, Integer> builder = dao.queryBuilder();
        PreparedQuery<Match> preparedQuery;
        try {
            preparedQuery = builder.where().eq(Match.WINNER_FIELD_NAME, id).or().eq(Match.LOOSER_FIELD_NAME, id).prepare();
            return dao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
