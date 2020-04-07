package com.android.tennistrackerapp.model.database;

import android.content.Context;
import android.util.Log;

import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.managers.MatchManager;
import com.android.tennistrackerapp.model.database.managers.MatchStatManager;
import com.android.tennistrackerapp.model.database.managers.PlayerManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
            this.playerManager = new PlayerManager((Dao<Player, Integer>) helper.getDao(Player.class));
            this.matchManager = new MatchManager((Dao<Match, Integer>) helper.getDao(Match.class));
            this.matchStatManager = new MatchStatManager((Dao<MatchStat, Integer>) helper.getDao(MatchStat.class));

            initIfNeeded();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBHelper getHelper() {
        return helper;
    }

    private void initIfNeeded() {
        ArrayList<Player> players = (ArrayList<Player>) playerManager.getAll();

        if (players.isEmpty()) {
            Player player1 = new Player("Daniel", 1, "");
            Player player2 = new Player("Victor", 2, "");
            this.playerManager.createOne(new Player("Daniel", 1, ""));
            this.playerManager.createOne(new Player("Victor", 2, ""));

            this.matchManager.createOne(new Match(player1, player2, new Date(), null));

            Log.d("DATABASE", "element inserted");
        }
    }

}
