package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jochem on 09-06-15.
 */
public class Statistics {
    @SerializedName("max_frags_tank_id")
    private int maxFragsTankId;

    @SerializedName("all")
    private AllStatistics allStatistics;

    @SerializedName("trees_cut")
    private int treesCut;

    @SerializedName("max_xp")
    private int maxXp;

    @SerializedName("battles")
    private int battleCount;

    public Statistics(int maxFragsTankId, AllStatistics allStatistics, int treesCut, int maxXp, int battleCount) {
        this.maxFragsTankId = maxFragsTankId;
        this.allStatistics = allStatistics;
        this.treesCut = treesCut;
        this.maxXp = maxXp;
        this.battleCount = battleCount;
    }

    public int getMaxFragsTankId() {
        return maxFragsTankId;
    }

    public void setMaxFragsTankId(int maxFragsTankId) {
        this.maxFragsTankId = maxFragsTankId;
    }

    public AllStatistics getAllStatistics() {
        return allStatistics;
    }

    public void setAllStatistics(AllStatistics allStatistics) {
        this.allStatistics = allStatistics;
    }

    public int getTreesCut() {
        return treesCut;
    }

    public void setTreesCut(int treesCut) {
        this.treesCut = treesCut;
    }

    public int getMaxXp() {
        return maxXp;
    }

    public void setMaxXp(int maxXp) {
        this.maxXp = maxXp;
    }

    public int getBattleCount() {
        return battleCount;
    }

    public void setBattleCount(int battleCount) {
        this.battleCount = battleCount;
    }
}
