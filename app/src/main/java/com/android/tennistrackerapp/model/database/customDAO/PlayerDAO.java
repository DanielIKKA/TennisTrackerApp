package com.android.tennistrackerapp.model.database.customDAO;


import com.android.tennistrackerapp.model.Player;
import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;

public class PlayerDAO extends BaseDaoImpl<Player, Integer> {
    protected PlayerDAO(Class<Player> dataClass) throws SQLException {
        super(dataClass);
    }
}
