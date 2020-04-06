package com.android.tennistrackerapp.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "statistics")
public class MatchStat {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ForeignKey(entity = Match.class, parentColumns = "id", childColumns = "id_match")
    private long id_match;
    @ForeignKey(entity = Player.class, parentColumns = "id", childColumns = "id_user")
    private long id_player;

    private int nbDoubleFault;
    private float percentFirstService;
    private float percentSecondService;
    private int nbBreakBalls;
    private int nbWinPoint;
    private int nbWinGame;
    private int maxGameSeries;

    // ----------------
    // CONSTRUCTOR
    // ----------------
    public MatchStat(long id_match, long id_player, int nbDoubleFault, float percentFirstService, float percentSecondService, int nbBreakBalls, int nbWinPoint, int nbWinGame, int maxGameSeries) {
        this.id_match = id_match;
        this.id_player = id_player;
        this.nbDoubleFault = nbDoubleFault;
        this.percentFirstService = percentFirstService;
        this.percentSecondService = percentSecondService;
        this.nbBreakBalls = nbBreakBalls;
        this.nbWinPoint = nbWinPoint;
        this.nbWinGame = nbWinGame;
        this.maxGameSeries = maxGameSeries;
    }

    // ----------------
    // GETTERS
    // ----------------
    public long getId() { return id; }
    public long getId_match() { return id_match; }
    public long getId_player() { return id_player; }
    public int getNbDoubleFault() { return nbDoubleFault; }
    public float getPercentFirstService() { return percentFirstService; }
    public float getPercentSecondService() { return percentSecondService; }
    public int getNbBreakBalls() { return nbBreakBalls; }
    public int getNbWinPoint() { return nbWinPoint; }
    public int getNbWinGame() { return nbWinGame; }
    public int getMaxGameSeries() { return maxGameSeries; }

    // ----------------
    // SETTERS
    // ----------------
    public void setId(long id) { this.id = id; }
    public void setId_match(long id_match) { this.id_match = id_match; }
    public void setId_player(long id_player) { this.id_player = id_player; }
    public void setNbDoubleFault(int nbDoubleFault) { this.nbDoubleFault = nbDoubleFault; }
    public void setPercentFirstService(float percentFirstService) { this.percentFirstService = percentFirstService; }
    public void setPercentSecondService(float percentSecondService) { this.percentSecondService = percentSecondService; }
    public void setNbBreakBalls(int nbBreakBalls) { this.nbBreakBalls = nbBreakBalls; }
    public void setNbWinPoint(int nbWinPoint) { this.nbWinPoint = nbWinPoint; }
    public void setNbWinGame(int nbWinGame) { this.nbWinGame = nbWinGame; }
    public void setMaxGameSeries(int maxGameSeries) { this.maxGameSeries = maxGameSeries; }
}
