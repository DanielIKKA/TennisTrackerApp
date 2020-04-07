package com.android.tennistrackerapp.model.database.customDAO;


import com.android.tennistrackerapp.model.MatchStat;
import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

public class MatchStatDAO extends BaseDaoImpl<MatchStat, Integer> {
    protected MatchStatDAO(Class<MatchStat> dataClass) throws SQLException {
        super(dataClass);
    }
}
