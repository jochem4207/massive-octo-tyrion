package com.jdkmedia.vh8.domain;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by jochem on 31-05-15.
 */
public class PlayerDetailCard implements Serializable {
    private String mainTitle;
    private String subTitle;
    private int imageLocation;

    public PlayerDetailCard(String mainTitle, String subTitle, int imageLocation) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.imageLocation = imageLocation;
    }

    public String getMainTitle() {
        return mainTitle;
    }
    public String getSubTitle() {
        return subTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(int imageLocation) {
        this.imageLocation = imageLocation;
    }
}
