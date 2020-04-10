package com.android.tennistrackerapp.model.database.managers;

import com.android.tennistrackerapp.model.Player;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
