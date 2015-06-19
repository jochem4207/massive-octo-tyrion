package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jochem on 09-06-15.
 */
public class AllStatistics {
    private int spotted;
    private int frags;


    public AllStatistics(int spotted, int frags) {
        this.spotted = spotted;
        this.frags = frags;
    }

    public int getSpotted() {
        return spotted;
    }

    public void setSpotted(int spotted) {
        this.spotted = spotted;
    }

    public int getFrags() {
        return frags;
    }

    public void setFrags(int frags) {
        this.frags = frags;
    }
}