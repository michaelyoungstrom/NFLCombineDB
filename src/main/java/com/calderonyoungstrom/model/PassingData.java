package com.calderonyoungstrom.model;

public class PassingData {
    public final static String PASSING_ID_KEY = "Passing";

    private String passingId;
    private String playerId;
    private float compPerc;
    private int yards;
    private int touchdowns;
    private int interceptions;
    private float rating;

    //Constructor
    public PassingData(String passingId, String playerId, Float compPerc, int yards, int touchdowns, int interceptions,
                       float rating){
        this.passingId = passingId;
        this.playerId = playerId;
        this.compPerc = compPerc;
        this.yards = yards;
        this.touchdowns = touchdowns;
        this.interceptions = interceptions;
        this.rating = rating;
    }

    public float getCompPerc(){
        return compPerc;
    }

    public int getYard(){
        return yards;
    }

    public int getTouchdowns(){
        return touchdowns;
    }

    public int getInterceptions(){
        return interceptions;
    }

    public float getRating(){
        return rating;
    }

    public String getPassingId() {
        return passingId;
    }

    public void setPassingId(String passingId) {
        this.passingId = passingId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
