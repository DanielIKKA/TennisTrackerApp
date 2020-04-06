package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.database.customDAO.MatchDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatchManager {
    private MatchDAO dao;

    // --------------
    // CONSTRUCTOR
    // --------------
    public MatchManager(MatchDAO dao) {
        this.dao = dao;
    }

    // --------------
    // QUERIES
    // --------------
    public List<Match> getAllMatch() {
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
}
