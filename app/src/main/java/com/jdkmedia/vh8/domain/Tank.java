package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jochem on 21-06-15.
 */
public class Tank implements Serializable {
    @SerializedName("tank_id")
    private int id;
    @SerializedName("level")
    private int level;
    @SerializedName("image")
    private String image;
    @SerializedName("name_i18n")
    private String name;
    @SerializedName("contour_image")
    private String ContourImage;

    public Tank(int id, int level, String image, String name, String contourImage) {
        this.id = id;
        this.level = level;
        this.image = image;
        this.name = name;
        ContourImage = contourImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContourImage() {
        return ContourImage;
    }

    public void setContourImage(String contourImage) {
        ContourImage = contourImage;
    }
}
