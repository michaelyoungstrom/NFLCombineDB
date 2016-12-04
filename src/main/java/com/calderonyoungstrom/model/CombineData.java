package com.calderonyoungstrom.model;

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

    public float getHeight(){ return this.height; }

    public int getWeight(){ return this.weight; }

    public float getForty(){ return this.forty; }

    public float getTwenty(){ return this.twenty; }

    public float getThreecone(){ return this.threecone; }

    public float getVertical(){ return this.vertical; }

    public int getBroad(){ return this.broad; }

    public int getBench(){ return this.bench; }

    public String getCollege(){ return this.college; }

    public int getCombineYear(){ return this.combineYear; }
}
