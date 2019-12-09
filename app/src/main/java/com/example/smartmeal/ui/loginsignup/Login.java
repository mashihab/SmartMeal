package com.example.smartmeal.ui.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelInsertUserDetails;
import com.example.smartmeal.model.ModelUsers;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.AddDailyCost;
import com.example.smartmeal.ui.AddMeal;
import com.example.smartmeal.ui.UserActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    Button bt_signupPage,bt_login_mess;
    private Spinner spinner;
    private List<String> type;
    EditText Username,Password;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        bt_signupPage = findViewById(R.id.bt_go_signup_page);
        bt_login_mess = findViewById(R.id.bt_login_id);
        spinner = findViewById(R.id.spinner_calculator_id);

        Username = findViewById(R.id.et_username_id);
        Password = findViewById(R.id.et_password_id);

        type = new ArrayList<String>();
        type.add("Select");
        type.add("manager");
        type.add("member");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

    public void goSignUpPage(View view) {
        Intent intent = new Intent(Login.this,SignUp.class);
        startActivity(intent);
    }

    public void buttonLogin(View view) {



       String username = Username.getText().toString();
       String password = Password.getText().toString();

       if (spinner.getSelectedItem().equals("member")){


           final Retrofit instance = ApiClint.instance();

           apiInterface = instance.create(ApiInterface.class);

           apiInterface.loginMember(username,password).enqueue(new Callback<ModelUsers>() {
               @Override
               public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                   if (response.body().getResponse().equals("ok"))
                   {
                       //SharedPreferences to save user manager
                       MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
                       sharedPreferences.setUsername(response.body().getUsername());

                       Intent intent = new Intent(Login.this, UserActivity.class);
                       intent.putExtra("username",response.body().getUsername());
                       intent.putExtra("uniquetoken",response.body().getAdminuniquetoken());
                       startActivity(intent);
                   }
                   else if (response.body().getResponse().equals("failed")){
                       Toast.makeText(Login.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                   }

               }

               @Override
               public void onFailure(Call<ModelUsers> call, Throwable t) {

               }
           });


       }
       else if(spinner.getSelectedItem().equals("manager")){


           final Retrofit instance = ApiClint.instance();

           apiInterface = instance.create(ApiInterface.class);




           apiInterface.userLogin(username,password).enqueue(new Callback<ModelUsers>() {
               @Override
               public void onResponse(Call<ModelUsers> call, Response<ModelUsers> response) {
                   if (response.body().getResponse().equals("ok"))
                   {
                       // Toast.makeText(Login.this, "Success to Login and user is:  "+response.body().getUsername(), Toast.LENGTH_SHORT).show();

                       //SharedPreferences to save user manager
                       MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
                       sharedPreferences.setData(response.body().getUsername());
                       sharedPreferences.setEmail(response.body().getEmail());
                       sharedPreferences.setUniqueId(response.body().getMuniquetoken());

                       Intent intent = new Intent(Login.this, MainActivity.class);
                       intent.putExtra("username",response.body().getUsername());
                      intent.putExtra("email",response.body().getEmail());
                       startActivity(intent);
                   }
                   else if (response.body().getResponse().equals("failed")){
                       Toast.makeText(Login.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                   }

               }

               @Override
               public void onFailure(Call<ModelUsers> call, Throwable t) {

               }
           });
       }
       else if(spinner.getSelectedItem().equals("Select")){
           Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show();
       }




    }
}
