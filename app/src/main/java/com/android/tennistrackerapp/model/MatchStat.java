package com.android.tennistrackerapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.HashMap;

@DatabaseTable(tableName = "statistics")
public class MatchStat {

    public static final String PLAYER_ID = "id_player";
    public static final String MATCH_ID = "id_match";

    public static final String KEY_ACES = "Aces";
    public static final String KEY_DOUBLE_FAULT = "Doubles faults";
    public static final String KEY_PERCENT_FIRST_SERVICE = "First service";
    public static final String KEY_PERCENT_SECOND_SERVICE = "Second service";
    public static final String KEY_BREAK_BALLS = "Break balls";
    public static final String KEY_WIN_POINT = "Win points";
    public static final String KEY_GAME_SERIES = "Max game series";


    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(foreign = true, canBeNull = false, columnName = MATCH_ID, uniqueCombo = true)
    private Match match;
    @DatabaseField(foreign = true, canBeNull = false, columnName = PLAYER_ID, uniqueCombo = true)
    private Player player;
    @DatabaseField private Integer nbAces;
    @DatabaseField private Integer nbDoubleFault;
    @DatabaseField private Float percentFirstService;
    @DatabaseField private Float percentSecondService;
    @DatabaseField private Integer nbBreakBalls;
    @DatabaseField private Integer nbWinPoint;
    @DatabaseField private Integer nbWinGame;
    @DatabaseField private Integer maxGameSeries;
    @DatabaseField private Integer games_set1;
    @DatabaseField private Integer games_set2;
    @DatabaseField private Integer games_set3;


    public static final HashMap<String, Object> getNewDictionary(MatchStat stat) {
        HashMap<String, Object> dictionary = new HashMap<>();

        dictionary.put(KEY_ACES, stat.getNbAces());
        dictionary.put(KEY_DOUBLE_FAULT, stat.getNbDoubleFault());
        dictionary.put(KEY_PERCENT_FIRST_SERVICE, stat.getPercentFirstService());
        dictionary.put(KEY_PERCENT_SECOND_SERVICE, stat.getPercentSecondService());
        dictionary.put(KEY_BREAK_BALLS, stat.getNbBreakBalls());
        dictionary.put(KEY_WIN_POINT, stat.getNbWinGame());
        dictionary.put(KEY_GAME_SERIES, stat.getMaxGameSeries());

        return dictionary;
    }

    // ----------------
    // CONSTRUCTOR
    // ----------------
    public MatchStat(){}

    public MatchStat(Match match, Player player) {
        this(match, player, null,null, null, null,null,null,
                null,null,
                null,null,null);
    }

    public MatchStat(Match match, Player player, Integer aces, Integer nbDoubleFault, Float percentFirstService, Float percentSecondService, Integer nbBreakBalls,
                     Integer nbWinPoint, Integer nbWinGame, Integer maxGameSeries, Integer games_set1, Integer games_set2, Integer games_set3) {
        this.nbAces= aces;
        this.match = match;
        this.player = player;
        this.nbDoubleFault = nbDoubleFault;
        this.percentFirstService = percentFirstService;
        this.percentSecondService = percentSecondService;
        this.nbBreakBalls = nbBreakBalls;
        this.nbWinPoint = nbWinPoint;
        this.nbWinGame = nbWinGame;
        this.maxGameSeries = maxGameSeries;
        this.games_set1 = games_set1;
        this.games_set2 = games_set2;
        this.games_set3 = games_set3;
    }

    // ----------------
    // GETTERS
    // ----------------
    public int getId() { return id; }
    public Match getId_match() { return match; }
    public Player getPlayer() { return player; }
    public Integer getNbDoubleFault() { return nbDoubleFault; }
    public Float getPercentFirstService() { return percentFirstService; }
    public Float getPercentSecondService() { return percentSecondService; }
    public Integer getNbBreakBalls() { return nbBreakBalls; }
    public Integer getNbWinPoint() { return nbWinPoint; }
    public Integer getNbWinGame() { return nbWinGame; }
    public Integer getMaxGameSeries() { return maxGameSeries; }
    public Integer getGames_set1() {  return games_set1; }
    public Integer getGames_set2() { return games_set2; }
    public Integer getGames_set3() { return games_set3; }

    // ----------------
    // SETTERS
    // ----------------
    public void setId(int id) { this.id = id; }
    public void setId_match(Match id_match) { this.match = id_match; }
    public void setPlayer(Player id_player) { this.player = id_player; }
    public void setNbDoubleFault(Integer nbDoubleFault) { this.nbDoubleFault = nbDoubleFault; }
    public void setPercentFirstService(Float percentFirstService) { this.percentFirstService = percentFirstService; }
    public void setPercentSecondService(Float percentSecondService) { this.percentSecondService = percentSecondService; }
    public void setNbBreakBalls(Integer nbBreakBalls) { this.nbBreakBalls = nbBreakBalls; }
    public void setNbWinPoint(Integer nbWinPoint) { this.nbWinPoint = nbWinPoint; }
    public void setNbWinGame(Integer nbWinGame) { this.nbWinGame = nbWinGame; }
    public void setMaxGameSeries(Integer maxGameSeries) { this.maxGameSeries = maxGameSeries; }
    public void setGames_set1(Integer games_set1) { this.games_set1 = games_set1; }
    public void setGames_set2(Integer games_set2) { this.games_set2 = games_set2; }
    public void setGames_set3(Integer games_set3) { this.games_set3 = games_set3; }

    public Integer getNbAces() {
        return nbAces;
    }

    public void setNbAces(Integer nbAces) {
        this.nbAces = nbAces;
    }
}
