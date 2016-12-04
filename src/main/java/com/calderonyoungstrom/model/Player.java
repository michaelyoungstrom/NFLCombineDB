package com.calderonyoungstrom.model;

public class Player {

    private String playerId;
    private String fname;
    private String lname;
    private String team;

    private CombineData combine;
    private PassingData passingData;
    private RushingData rushingData;
    private ReceivingData receivingData;

    //Constructor
    public Player(String playerId, String fname, String lname, String team){
        this.playerId = playerId;
        this.fname = fname;
        this.lname = lname;
        this.team = team;
    }

    public String getPlayerId(){
        return this.playerId;
    }

    public void setCombineData(CombineData combine){
        this.combine = combine;
    }

    public CombineData getCombineData(){
        return combine;
    }

    public void setPassingData(PassingData passingData){
        this.passingData = passingData;
    }

    public PassingData getPassingData(){
        return passingData;
    }

    public void setRushingData(RushingData rushingData){
        this.rushingData = rushingData;
    }

    public RushingData getRushingData(){
        return rushingData;
    }

    public void setReceivingData(ReceivingData receivingData){
        this.receivingData = receivingData;
    }

    public ReceivingData getReceivingData(){
        return receivingData;
    }
}
