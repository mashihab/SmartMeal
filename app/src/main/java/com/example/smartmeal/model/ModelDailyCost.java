package com.example.smartmeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDailyCost {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("uniquetoken")
    @Expose
    private String uniquetoken;
    @SerializedName("daily_expense")
    @Expose
    private int daily_expense;
    @SerializedName("bazar_list")
    @Expose
    private String bazar_list;
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("deposit")
    @Expose
    private int deposit;

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUniquetoken() {
        return uniquetoken;
    }

    public void setUniquetoken(String uniquetoken) {
        this.uniquetoken = uniquetoken;
    }

    public int getDaily_expense() {
        return daily_expense;
    }

    public void setDaily_expense(int daily_expense) {
        this.daily_expense = daily_expense;
    }

    public String getBazar_list() {
        return bazar_list;
    }

    public void setBazar_list(String bazar_list) {
        this.bazar_list = bazar_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
