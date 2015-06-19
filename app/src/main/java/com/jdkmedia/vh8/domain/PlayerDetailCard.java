package com.jdkmedia.vh8.domain;

import android.media.Image;

/**
 * Created by jochem on 31-05-15.
 */
public class PlayerDetailCard {
    private String mainTitle;
    private String subTitle;
    private Image image;

    public PlayerDetailCard(String mainTitle, String subTitle, Image image) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.image = image;
    }

    public Image getImage() {
        return image;
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
    public void setImage(Image image) {
        this.image = image;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
