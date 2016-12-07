package com.calderonyoungstrom.model;

public class RushingData {

    public final static String RUSHING_ID_KEY = "Rushing";

    private String rushingId;
    private String playerId;
    private int yards;
    private int touchdowns;
    private int longest;
    private float yardsPerAttempt;
    private float yardsPerGame;

    //Constructor
    public RushingData(String rushingId, String playerId, int yards, int touchdowns, int longest, float yardsPerAttempt,
                       float yardsPerGame){
        this.rushingId = rushingId;
        this.playerId = playerId;
        this.yards = yards;
        this.touchdowns = touchdowns;
        this.longest = longest;
        this.yardsPerAttempt = yardsPerAttempt;
        this.yardsPerGame = yardsPerGame;
    }

    public int getYards(){ return yards; }

    public int getTouchdowns(){ return touchdowns; }

    public int getLongest(){ return longest; }

    public float getYardsPerAttempt(){ return yardsPerAttempt; }

    public float getYardsPerGame(){ return yardsPerGame; }

}
