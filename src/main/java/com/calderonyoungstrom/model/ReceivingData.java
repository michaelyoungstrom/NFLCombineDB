package com.calderonyoungstrom.model;

/**
 * Created by mayoungstrom on 12/4/16.
 */
public class ReceivingData {

    private String receivingId;
    private String playerId;
    private int receptions;
    private float catchPerc;
    private int yards;
    private float yardsPerRec;
    private int touchdowns;
    private float yardsPerGame;

    //Constructor
    public ReceivingData(String receivingId, String playerId, int receptions, float catchPerc, int yards,
                         float yardsPerRec, int touchdowns, float yardsPerGame){
        this.receivingId = receivingId;
        this.playerId = playerId;
        this.receptions = receptions;
        this.catchPerc = catchPerc;
        this.yards = yards;
        this.yardsPerRec = yardsPerRec;
        this.touchdowns = touchdowns;
        this.yardsPerGame = yardsPerGame;
    }

    public String getReceivingId(){ return receivingId; }

    public String getPlayerId(){ return playerId; }

    public int getReceptions(){ return receptions; }

    public float getCatchPerc(){ return catchPerc; }

    public int getYards(){ return yards; }

    public float getYardsPerRec(){ return yardsPerRec; }

    public int getTouchdowns(){ return touchdowns; }

    public float getYardsPerGame(){ return yardsPerGame; }

}
