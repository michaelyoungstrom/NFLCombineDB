package com.calderonyoungstrom.model;

/**
 * Created by mayoungstrom on 12/4/16.
 */
public class CombineData {

    private String combineId;
    private String playerId;
    private float height;
    private int weight;
    private float forty;
    private float twenty;
    private float threecone;
    private float vertical;
    private int broad;
    private int bench;
    private String college;
    private int combineYear;

    //Constructor
    public CombineData(String combineId, String playerId, float height, int weight, float forty, float twenty,
                       float threecone, float vertical, int broad, int bench, String college, int combineYear){
        this.combineId = combineId;
        this.playerId = playerId;
        this.height = height;
        this.weight = weight;
        this.forty = forty;
        this.twenty = twenty;
        this.threecone = threecone;
        this.vertical = vertical;
        this.broad = broad;
        this.bench = bench;
        this.college = college;
        this.combineYear = combineYear;
    }

    public String getCombineId() { return getCombineId(); }

    public String getPlayerId() { return playerId; }

    public float getHeight(){ return height; }

    public int getWeight(){ return weight; }

    public float getForty(){ return forty; }

    public float getTwenty(){ return twenty; }

    public  float getThreecone(){ return threecone; }

    public float getVertical(){ return vertical; }

    public int getBroad(){ return broad; }

    public int getBench(){ return bench; }

    public String getCollege(){ return college; }

    public int getCombineYear(){ return combineYear; }
}
