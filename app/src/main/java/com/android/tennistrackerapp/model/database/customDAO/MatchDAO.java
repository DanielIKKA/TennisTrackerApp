package com.android.tennistrackerapp.model.database.customDAO;


import com.android.tennistrackerapp.model.Match;
import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

public class MatchDAO extends BaseDaoImpl<Match, Integer> {
    protected MatchDAO(Class<Match> dataClass) throws SQLException {
        super(dataClass);
    }
}

