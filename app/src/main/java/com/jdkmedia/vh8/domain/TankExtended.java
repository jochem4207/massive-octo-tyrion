package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jochem on 21-06-15.
 */
public class TankExtended implements Serializable{

    @SerializedName("engine_power")
    private int enginePower;
    @SerializedName("weight")
    private int weight;
    @SerializedName("gun_damage_min")
    private int gunDamageMin;
    @SerializedName("image")
    private String image;
    @SerializedName("is_premium")
    private boolean premium;
    @SerializedName("short_name_i18n")
    private String name;
    @SerializedName("name_i18n")
    private String longName;
    @SerializedName("max_health")
    private int maxHealth;
    @SerializedName("tank_id")
    private int id;
    @SerializedName("radio_distance")
    private int radioDistance;
    @SerializedName("type_i18n")
    private String type; //medium tank etc
    @SerializedName("speed_limit")
    private int speedLimit;

    public TankExtended(int enginePower, int weight, int gunDamageMin, String image, boolean premium, String name, String longName, int maxHealth, int id, int radioDistance, String type, int speedLimit) {
        this.enginePower = enginePower;
        this.weight = weight;
        this.gunDamageMin = gunDamageMin;
        this.image = image;
        this.premium = premium;
        this.name = name;
        this.longName = longName;
        this.maxHealth = maxHealth;
        this.id = id;
        this.radioDistance = radioDistance;
        this.type = type;
        this.speedLimit = speedLimit;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGunDamageMin() {
        return gunDamageMin;
    }

    public void setGunDamageMin(int gunDamageMin) {
        this.gunDamageMin = gunDamageMin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRadioDistance() {
        return radioDistance;
    }

    public void setRadioDistance(int radioDistance) {
        this.radioDistance = radioDistance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
