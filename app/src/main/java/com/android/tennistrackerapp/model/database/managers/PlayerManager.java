package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.Player;
import com.android.tennistrackerapp.model.database.DBManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class PlayerManager {
    private Dao<Player, Integer> dao;

    // --------------
    // CONSTRUCTOR
    // --------------
    public PlayerManager(Dao<Player, Integer> dao) {
        this.dao = dao;
    }

    // --------------
    // QUERIES
    // --------------
    public List<Player> getAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Player>();
        }
    }

    @Nullable
    public Player getById(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Player newPlayer) {
        try {
            this.dao.create(newPlayer);
            return newPlayer.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getErrorCode();
        }
    }

    // Cascade implementation
    public void deleteOneById(Integer id) {
        DBManager manager = DBManager.getInstance();
        try {
            ArrayList<Match> children = (ArrayList<Match>) manager.getMatchManager().getAllMatchWith(id);
            manager.getMatchManager().delete(children);
            this.dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOne(Player player) {
        try {
            dao.deleteById(player.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Player player) {
        try {
            this.dao.update(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
