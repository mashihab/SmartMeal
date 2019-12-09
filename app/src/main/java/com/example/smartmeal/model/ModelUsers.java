package com.example.smartmeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUsers {

    @SerializedName("response")
    private String Response;

    @SerializedName("username")
    private  String Username;

    @SerializedName("email")
   private  String Email;

    @SerializedName("muniquetoken")
    private  String Muniquetoken;

    @SerializedName("adminuniquetoken")
    private  String Adminuniquetoken;

    public String getAdminuniquetoken() {
        return Adminuniquetoken;
    }



    public void setAdminuniquetoken(String adminuniquetoken) {
        Adminuniquetoken = adminuniquetoken;
    }

    public void setMuniquetoken(String muniquetoken) {
        Muniquetoken = muniquetoken;
    }

    @SerializedName("meal_rate")
    @Expose
    private String mealRate;

    public String getMealRate() {
        return mealRate;
    }

    public void setMealRate(String mealRate) {
        this.mealRate = mealRate;
    }

    public String getMuniquetoken() {
        return Muniquetoken;
    }

    public String getEmail() {
        return Email;
    }

    public String getResponse() {
        return Response;
    }

    public String getUsername() {
        return Username;
    }
}
