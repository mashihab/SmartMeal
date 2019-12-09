package com.example.smartmeal.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static MySharedPreferences mySharedPreferences=null;

    private MySharedPreferences(Context context)
    {
        sharedPreferences = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public static synchronized MySharedPreferences getPreferences(Context context)
    {
        if(mySharedPreferences==null) {
            mySharedPreferences = new MySharedPreferences(context);
        }
        return mySharedPreferences;
    }

    public void setData(String user)
    {
        editor.putString("login",user);
        editor.apply();
    }

    public String getData()
    {
        return sharedPreferences.getString("login","none");
    }

    public void setEmail(String email)
    {
        editor.putString("email",email);
        editor.apply();
    }

    public String getTotalmeal()
    {
        return sharedPreferences.getString("totalmeal","null");
    }

    public void setTotalmeal(String totalmeal)
    {
        editor.putString("totalmeal",totalmeal);
        editor.apply();
    }

    public String getEmail()
    {
        return sharedPreferences.getString("email","none");
    }


    public void setUsername(String username)
    {
        editor.putString("username",username);
        editor.apply();
    }

    public String getUsername()
    {
        return sharedPreferences.getString("username","none");
    }

    public void setUniqueId(String uniqueId)
    {
        editor.putString("uniqueId",uniqueId);
        editor.apply();
    }

    public String getUniqueId()
    {
        return sharedPreferences.getString("uniqueId","null");
    }

}
