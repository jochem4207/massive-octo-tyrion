package com.jdkmedia.vh8.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jochem on 29-04-15.
 */

public class Player implements Serializable{

    @SerializedName("account_id")
    private int accountId;
    @SerializedName("nickname")
    private String nickName;

    public Player(int accountId, String nickName) {
        this.accountId = accountId;
        this.nickName = nickName;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getNickName() {
        return nickName;
    }

    public String toString() {
        return this.accountId + this.nickName;
    }
}
