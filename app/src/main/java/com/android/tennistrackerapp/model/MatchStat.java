package com.android.tennistrackerapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "statistics")
public class MatchStat {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(foreign = true, canBeNull = false, columnName = "id_match", uniqueCombo = true)
    private Match match;
    @DatabaseField(foreign = true, canBeNull = false, columnName = "id_player", uniqueCombo = true)
    private Player player;
    @DatabaseField private int nbDoubleFault;
    @DatabaseField private float percentFirstService;
    @DatabaseField private float percentSecondService;
    @DatabaseField private int nbBreakBalls;
    @DatabaseField private int nbWinPoint;
    @DatabaseField private int nbWinGame;
    @DatabaseField private int maxGameSeries;

    // ----------------
    // CONSTRUCTOR
    // ----------------
    public MatchStat(){}
    public MatchStat(Match id_match, Player id_player, int nbDoubleFault, float percentFirstService, float percentSecondService, int nbBreakBalls, int nbWinPoint, int nbWinGame, int maxGameSeries) {
        this.match = id_match;
        this.player = id_player;
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
    public int getId() { return id; }
    public Match getId_match() { return match; }
    public Player getId_player() { return player; }
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
    public void setId(int id) { this.id = id; }
    public void setId_match(Match id_match) { this.match = id_match; }
    public void setId_player(Player id_player) { this.player = id_player; }
    public void setNbDoubleFault(int nbDoubleFault) { this.nbDoubleFault = nbDoubleFault; }
    public void setPercentFirstService(float percentFirstService) { this.percentFirstService = percentFirstService; }
    public void setPercentSecondService(float percentSecondService) { this.percentSecondService = percentSecondService; }
    public void setNbBreakBalls(int nbBreakBalls) { this.nbBreakBalls = nbBreakBalls; }
    public void setNbWinPoint(int nbWinPoint) { this.nbWinPoint = nbWinPoint; }
    public void setNbWinGame(int nbWinGame) { this.nbWinGame = nbWinGame; }
    public void setMaxGameSeries(int maxGameSeries) { this.maxGameSeries = maxGameSeries; }
}
