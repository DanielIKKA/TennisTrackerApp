package com.android.tennistrackerapp.model.database;

import android.content.Context;

import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.customDAO.MatchDAO;
import com.android.tennistrackerapp.model.database.customDAO.MatchStatDAO;
import com.android.tennistrackerapp.model.database.customDAO.PlayerDAO;
import com.android.tennistrackerapp.model.database.managers.MatchManager;
import com.android.tennistrackerapp.model.database.managers.MatchStatManager;
import com.android.tennistrackerapp.model.database.managers.PlayerManager;

import java.sql.SQLException;

/**
 * One of sources : https://gist.github.com/adrienfenech/4d8bbcb441b24f994f51
 * Official library Documentation : https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html#Top
 */
public class DBManager {
    private static DBManager managerInstance;
    private DBHelper helper;

    // ---------------
    // MANAGERS
    // ---------------
    private PlayerManager playerManager;
    private MatchManager matchManager;
    private MatchStatManager matchStatManager;

    public static void Init(Context context) {
        if (managerInstance == null)
            managerInstance = new DBManager(context);
    }

    public static DBManager getInstance() {
        return managerInstance;
    }

    private DBManager(Context context) {
        helper = new DBHelper(context);

        try {
            this.playerManager = new PlayerManager((PlayerDAO) helper.getDao(Player.class));
            this.matchManager = new MatchManager((MatchDAO) helper.getDao(Match.class));
            this.matchStatManager = new MatchStatManager((MatchStatDAO) helper.getDao(MatchStat.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBHelper getHelper() {
        return helper;
    }

}
