package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jochem on 09-06-15.
 */
public class PlayerTanks {
    private List<PlayerTank> playerTankList;

    public PlayerTanks(List<PlayerTank> playerTankList) {
        this.playerTankList = playerTankList;
    }

    public List<PlayerTank> getPlayerTankList() {
        return playerTankList;
    }

    public void setPlayerTankList(List<PlayerTank> playerTankList) {
        this.playerTankList = playerTankList;
    }

    private class PlayerTank{
        @SerializedName("mark_of_mastery")
        public int markOfMastery;
        @SerializedName("tank_id")
        public int tankId;
    }
}