package com.android.tennistrackerapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.tennistrackerapp.model.Match;
import com.android.tennistrackerapp.model.MatchStat;
import com.android.tennistrackerapp.model.Player;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import androidx.annotation.Nullable;


/**
 * One of sources : https://gist.github.com/adrienfenech/4d8bbcb441b24f994f51
 * Official library Documentation : https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html#Top
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "data.db";
    private static final int DB_VERSION = 5;

    // -------------
    // CONSTRUCTOR
    // -------------
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Player.class);
            TableUtils.createTableIfNotExists(connectionSource, Match.class);
            TableUtils.createTableIfNotExists(connectionSource, MatchStat.class);

            Log.d("DATABASE", "on create database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Player.class, true);
            TableUtils.dropTable(connectionSource, Match.class, true);
            TableUtils.dropTable(connectionSource, MatchStat.class, true);

            onCreate(sqLiteDatabase, connectionSource);
            Log.d("DATABASE", "on upgrade database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}