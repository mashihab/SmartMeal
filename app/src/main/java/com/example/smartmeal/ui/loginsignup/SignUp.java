package com.example.smartmeal.ui.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelInsertUserDetails;
import com.example.smartmeal.model.ModelInsertadminDetails;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;

import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button createAccountButton;

    ApiInterface apiInterface;

    EditText username,email,phone,password,uniquetoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        radioGroup = findViewById(R.id.radio_group);

        username = findViewById(R.id.et_member_userId);
        email = findViewById(R.id.et_member_emailId);
        phone = findViewById(R.id.et_member_phoneId);
        password = findViewById(R.id.et_member_password);

        uniquetoken = findViewById(R.id.et_member_tokenId);

        createAccountButton = findViewById(R.id.bt_create_profil);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (radioButton.getText().equals("No"))
                {



                    String getUsername = username.getText().toString().trim();
                    String getemail = email.getText().toString().trim();
                    String getPhone = phone.getText().toString().trim();
                    String getPassword = password.getText().toString().trim();
                    String getUniquetoken = uniquetoken.getText().toString().trim();


                    if(getUsername.isEmpty() || getemail.isEmpty() || getPhone.isEmpty() || getPassword.isEmpty()|| getUniquetoken.isEmpty()){


                        username.setError("Enter Username");
                        email.setError("Enter Email");
                        phone.setError("Enter Phone no");
                        password.setError("Enter Password");
                        uniquetoken.setError("Enter unique token");

                    }else {

                        ModelInsertUserDetails modelInsertUserDetails = new ModelInsertUserDetails();
                        modelInsertUserDetails.setUsername(getUsername);
                        modelInsertUserDetails.setEmail(getemail);
                        modelInsertUserDetails.setPhone(getPhone);
                        modelInsertUserDetails.setPassword(getPassword);
                        modelInsertUserDetails.setAdminuniquetoken(getUniquetoken);



                        sendDatauser(modelInsertUserDetails);


                    }



                }
                if (radioButton.getText().equals("Yes"))
                {
                    try {


                        String getUsername = username.getText().toString().trim();
                        String getemail = email.getText().toString().trim();
                        String getPhone = phone.getText().toString().trim();
                        String getPassword = password.getText().toString().trim();

                        String getUniquetoken = String.valueOf(gen());


                        if(getUsername.isEmpty() || getemail.isEmpty() || getPhone.isEmpty() || getPassword.isEmpty()){


                            username.setError("Enter Username");
                            email.setError("Enter Email");
                            phone.setError("Enter Phone no");
                            password.setError("Enter Password");

                        }else {

                            ModelInsertadminDetails modelInsertadminDetails = new ModelInsertadminDetails();
                            modelInsertadminDetails.setUsername(getUsername);
                            modelInsertadminDetails.setEmail(getemail);
                            modelInsertadminDetails.setPhone(getPhone);
                            modelInsertadminDetails.setPassword(getPassword);
                            modelInsertadminDetails.setMuniquetoken(getUniquetoken);



                            sendData(modelInsertadminDetails);


                            ModelInsertUserDetails modelInsertUserDetails = new ModelInsertUserDetails();
                            modelInsertUserDetails.setUsername(getUsername);
                            modelInsertUserDetails.setEmail(getemail);
                            modelInsertUserDetails.setPhone(getPhone);
                            modelInsertUserDetails.setPassword(getPassword);
                            modelInsertUserDetails.setAdminuniquetoken(getUniquetoken);



                            sendDatauser(modelInsertUserDetails);


                        }
                        
                    }catch (Exception e){
                        Toast.makeText(SignUp.this, "Select a readio Button", Toast.LENGTH_SHORT).show();
                    }
                    
            }}
        });

    }

    public void checkButton(View v){
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);

         //   Toast.makeText(this, "" + radioButton.getText(), Toast.LENGTH_SHORT).show();

            if (radioButton.getText().equals("No")) {




                uniquetoken.setVisibility(View.VISIBLE);
            }
            if (radioButton.getText().equals("Yes")) {
                uniquetoken.setVisibility(View.GONE);
            }
        }


    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    private void sendData(ModelInsertadminDetails modelInsertadminDetails) {

        final Retrofit instance = ApiClint.instance();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.inputmanagerData(modelInsertadminDetails).enqueue(new Callback<ModelInsertadminDetails>() {
            @Override
            public void onResponse(Call<ModelInsertadminDetails> call, Response<ModelInsertadminDetails> response) {
//                Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ModelInsertadminDetails> call, Throwable t) {
                Log.d("Throwable",""+t.getMessage());
            }
        });



    }

    private void sendDatauser(ModelInsertUserDetails modelInsertUserDetails) {

        final Retrofit instance = ApiClint.instance();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.inputuserData(modelInsertUserDetails).enqueue(new Callback<ModelInsertUserDetails>() {
            @Override
            public void onResponse(Call<ModelInsertUserDetails> call, Response<ModelInsertUserDetails> response) {
//                Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ModelInsertUserDetails> call, Throwable t) {
                Log.d("Throwable",""+t.getMessage());
            }
        });



    }


}