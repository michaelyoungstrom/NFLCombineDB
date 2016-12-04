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


}
