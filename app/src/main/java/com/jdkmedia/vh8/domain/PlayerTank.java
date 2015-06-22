package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jochem on 19-06-15.
 */
public class PlayerTank implements Serializable{

    @SerializedName("tank_id")
    private int tankId;

    @SerializedName("mark_of_mastery")
    private int markOfMastery;

    public int getTankId() {
        return tankId;
    }

    public int getMarkOfMastery() {
        return markOfMastery;
    }

    public void setMarkOfMastery(int markOfMastery) {
        this.markOfMastery = markOfMastery;
    }

    public void setTankId(int tankId) {
        this.tankId = tankId;
    }
}