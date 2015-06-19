package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jochem on 24-05-15.
 */
public class PlayerExtended implements Serializable {
    @SerializedName("nickname")
    private String nickName;
    @SerializedName("account_id")
    private Integer accountId;
    @SerializedName("client_language")
    private String clientLanguage;
    @SerializedName("created_at")
    private long createdAt;
    @SerializedName("global_rating")
    private int globalRating;

    private String accessToken;
    private List<PlayerTank> playerTankList;
    private Statistics statistics;

    public PlayerExtended(String nickName, int accountId, String accessToken){
        this.nickName = nickName;
        this.accountId = accountId;
        this.accessToken = accessToken;
    }


    public int getGlobalRating() {
        return globalRating;
    }
    public String getNickname() {
        return nickName;
    }
    public Integer getAccountId() { return accountId; }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    public String getClientLanguage() {
        return clientLanguage;
    }
    public void setClientLanguage(String clientLanguage) {
        this.clientLanguage = clientLanguage;
    }
    public long getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    public void setGlobalRating(int globalRating) {
        this.globalRating = globalRating;
    }
    public List<PlayerTank> getPlayerTankList() {
        return playerTankList;
    }
    public void setPlayerTankList(List<PlayerTank> playerTankList) {
        this.playerTankList = playerTankList;
    }



    //Information from player

    /**
     * Get the number of tanks a person has
     * @return: the amount of tanks
     */
    public int getTankCount(){
        return this.playerTankList.size();
    }

    /**
     * Get the count of the marks of excellence a player got
     * @param level 1-4
     * @return amount: The amount of marksofmasteries
     */
    public int getMarksOfExcellenceCount(int level){
        int amount = 0;

        int i = 0;
        while (i < this.playerTankList.size()) {
            if(playerTankList.get(i).getMarkOfMastery() == level){
                amount++;
            }
            i++;
        }

        return amount;
    }
}
