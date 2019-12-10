package com.example.smartmeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAddMeal {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("adminuniquetoken")
    @Expose
    private String adminuniquetoken;

    public String getAdminuniquetoken() {
        return adminuniquetoken;
    }

    public void setAdminuniquetoken(String adminuniquetoken) {
        this.adminuniquetoken = adminuniquetoken;
    }

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("check_meal")
    @Expose
    private String checkMeal;
    @SerializedName("number_of_meal")
    @Expose
    private int numberOfMeal;

    @SerializedName("breakfast")
    @Expose
    private String breakfast;

    @SerializedName("lunch")
    @Expose
    private String lunch;

    @SerializedName("dinner")
    @Expose
    private String dinner;

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    @SerializedName("usertotalmeal")
    @Expose
    private String usertotalmeal;

    @SerializedName("messdailyexpense")
    @Expose
    private String messdailyexpense;

    @SerializedName("userdeposit")
    @Expose
    private String userdeposit;


    @SerializedName("mess_total_balance")
    @Expose
    private String mess_total_balance;

    @SerializedName("user_cost")
    @Expose
    private String user_cost;


    @SerializedName("user_balance")
    @Expose
    private String user_balance;


    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }

    public String getUser_cost() {
        return user_cost;
    }

    public void setUser_cost(String user_cost) {
        this.user_cost = user_cost;
    }

    public String getMess_total_balance() {
        return mess_total_balance;
    }

    public void setMess_total_balance(String mess_total_balance) {
        this.mess_total_balance = mess_total_balance;
    }

    public String getUserdeposit() {
        return userdeposit;
    }

    public void setUserdeposit(String userdeposit) {
        this.userdeposit = userdeposit;
    }

    public String getMessdailyexpense() {
        return messdailyexpense;
    }

    public void setMessdailyexpense(String messdailyexpense) {
        this.messdailyexpense = messdailyexpense;
    }

    public String getUsertotalmeal() {
        return usertotalmeal;
    }

    public void setUsertotalmeal(String usertotalmeal) {
        this.usertotalmeal = usertotalmeal;
    }

    @SerializedName("totalmeal")
    @Expose
    private String totalmeal;

    @SerializedName("meal_rate")
    @Expose
    private String Meal_rate;

    public String getMeal_rate() {
        return Meal_rate;
    }

    public void setMeal_rate(String meal_rate) {
        Meal_rate = meal_rate;
    }


    @SerializedName("response")
    @Expose
    private String response;


    @SerializedName("totalmember")
    @Expose
    private String totalmember;

    public String getTotalmember() {
        return totalmember;
    }

    public void setTotalmember(String totalmember) {
        this.totalmember = totalmember;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTotalmeal() {
        return totalmeal;
    }

    public void setTotalmeal(String totalmeal) {
        this.totalmeal = totalmeal;
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

    public String getCheckMeal() {
        return checkMeal;
    }

    public void setCheckMeal(String checkMeal) {
        this.checkMeal = checkMeal;
    }

    public int getNumberOfMeal() {
        return numberOfMeal;
    }

    public void setNumberOfMeal(int numberOfMeal) {
        this.numberOfMeal = numberOfMeal;
    }


}
