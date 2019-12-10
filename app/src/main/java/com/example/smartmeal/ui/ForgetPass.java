package com.example.smartmeal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelUsers;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.loginsignup.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPass extends AppCompatActivity {
    
    EditText username,newpassword;
    Button changepass;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        
        username = findViewById(R.id.et_username_id_for);
        newpassword = findViewById(R.id.et_password_id_for);
        changepass = findViewById(R.id.bt_change_id_for);
        
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamee =username.getText().toString();
               String pass = newpassword.getText().toString();
               
               if (TextUtils.isEmpty(usernamee)){
                   username.setError("Enter Username");
               }
              if (TextUtils.isEmpty(pass)){
                   newpassword.setError("Enter New Password");
                }
               else 
               {


                   final Retrofit instance = ApiClint.instance();

                   apiInterface = instance.create(ApiInterface.class);

                   apiInterface.changepassword(usernamee,pass).enqueue(new Callback<ModelUsers>() {
                       @Override
                       public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                           if (response.body().getResponse().equals("ok"))
                           {

                               Toast.makeText(ForgetPass.this, "Password Change successful", Toast.LENGTH_SHORT).show();
                           }
                           else if (response.body().getResponse().equals("failed")){
                               Toast.makeText(ForgetPass.this, "username not match", Toast.LENGTH_SHORT).show();
                           }

                       }

                       @Override
                       public void onFailure(Call<ModelUsers> call, Throwable t) {

                       }
                   });


               }
                
                    
                
            }
        });
        
    }
}
