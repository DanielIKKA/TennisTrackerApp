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
            Player player1 = new Player("Daniel Ikka", 1, 21);
            Player player2 = new Player("Victor Aymard", 2, 22);
            Player player3 = new Player("Rene Cotti", 2, 22);
            this.playerManager.createOne(player1);
            this.playerManager.createOne(player2);
            this.playerManager.createOne(player3);

            Match match1 = new Match(player1, player2, new Date(), null);
            Match match2 = new Match(player3, player1, new Date(), null);
            this.matchManager.createOne(match1); // Daniel - Victor
            this.matchManager.createOne(match2); // Rene - Daniel

            MatchStat Stat1_1 = new MatchStat(match1, match1.getWinner(),3,2,(float)91.0,(float)100,7,2,
                    6,4,6,6,0);
            MatchStat Stat1_2 = new MatchStat(match1, match1.getLooser(), 3,6,(float)54.0,(float)90,1,0,
                    6,2,4,3,0);
            MatchStat Stat2_1 = new MatchStat(match2, match2.getWinner(),3,2,(float)91.0,(float)100,7,2,
                    6,4,6,2,7);
            MatchStat Stat2_2 = new MatchStat(match2, match2.getLooser(), 3,2,(float)91.0,(float)100,7,2,
                    6,4,1,6,5);
            this.matchStatManager.createOne(Stat1_1);
            this.matchStatManager.createOne(Stat1_2);
            this.matchStatManager.createOne(Stat2_1);
            this.matchStatManager.createOne(Stat2_2);

            Log.d("DATABASE", "element inserted");
        }
    }

    // ---------------
    // GETTERS
    // ---------------
    public PlayerManager getPlayerManager() { return playerManager; }
    public MatchManager getMatchManager() { return matchManager; }
    public MatchStatManager getMatchStatManager() { return matchStatManager; }
}
