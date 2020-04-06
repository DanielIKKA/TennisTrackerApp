package com.android.tennistrackerapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.android.tennistrackerapp.model.dao.MatchDAO;
import com.android.tennistrackerapp.model.dao.MatchStatDAO;
import com.android.tennistrackerapp.model.dao.PlayerDAO;

import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * References
 * https://developer.android.com/training/data-storage/room
 **/
@Database(entities = {Player.class, Match.class, MatchStat.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DB_NAME = "SQLite.db";
    private static final int VERSION = 1;

    // --- SINGLETON ---
    private static volatile DatabaseManager INSTANCE;

    // --- DAO ---
    public abstract PlayerDAO PlayerDAO();
    public abstract MatchDAO MatchDAO();
    public abstract MatchStatDAO MatchStatDAO();

    // --- INSTANCE ---
    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseManager.class, DB_NAME)
                            //.addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    /*private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Philippe");
                contentValues.put("urlPicture", "https://oc-user.imgix.net/users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");

                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }*/
}