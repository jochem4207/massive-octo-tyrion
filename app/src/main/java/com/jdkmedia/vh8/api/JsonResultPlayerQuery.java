package com.jdkmedia.vh8.api;

import com.google.gson.annotations.SerializedName;
import com.jdkmedia.vh8.domain.Meta;
import com.jdkmedia.vh8.domain.Player;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jochem on 06-05-15.
 */
public class JsonResultPlayerQuery implements Serializable{
   private String status;
   private Meta meta;
   @SerializedName("data")
   private List<Player> players;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Meta getMeta() {
        return meta;
    }

    public int getMetaCount(){
        return meta.getCount();
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}

