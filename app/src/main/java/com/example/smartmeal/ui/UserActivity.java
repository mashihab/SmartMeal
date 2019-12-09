package com.example.smartmeal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.model.ModelUsers;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.loginsignup.Login;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserActivity extends AppCompatActivity {


    Switch aSwitch;
    Animation frombottom;
    TextView logout,totalmeal,mealrate,mymeal,messtotalcost,eachuserdeposit,mycost,messtotalbalance,userbalance;
    CardView cardView;
    ApiInterface apiInterface;
    MySharedPreferences sharedPreferences;
    double meal_rate= 0.0;
    double userTotalMeal = 0.0;

    String startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        aSwitch = findViewById(R.id.sw_id);
        cardView = findViewById(R.id.cardview_memberall);
        logout = findViewById(R.id.tv_logout);
        totalmeal = findViewById(R.id.tv_user_totalmeal);
        mealrate = findViewById(R.id.tv_meal_rate);
        mymeal = findViewById(R.id.tv_my_total_meal);
        messtotalcost = findViewById(R.id.tv_mess_total_cost);
        eachuserdeposit = findViewById(R.id.tv_each_member_deposit);
        mycost = findViewById(R.id.tv_my_cost);
        messtotalbalance = findViewById(R.id.tv_mess_total_balance);
        userbalance = findViewById(R.id.tv_user_balance);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hhmmss");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        //textView.setText(dateTime);

        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        cardView.setAnimation(frombottom);

        settotalmeal();
        setmealrate();
        setusertotalmeal();
        setmesstotalexpense();
        seteachuserdeposit();
        setmesstotalBalance();
        setUserCost();
        setUserBalance();


        setmealofonTime();





        sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());

        if(sharedPreferences.getUsername().equals("none"))
        {
            startActivity(new Intent(UserActivity.this, Login.class));
        }
        else {

            Toast.makeText(this, "Congrats "+sharedPreferences.getUsername() +", You are already Logged in", Toast.LENGTH_SHORT).show();

        }

        int timecheck = Integer.parseInt(dateTime);

        if (timecheck>123400 && timecheck<123500){

            aSwitch.setVisibility(View.GONE);
        }else
        {
            aSwitch.setVisibility(View.VISIBLE);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.setUsername("none");
                startActivity(new Intent(UserActivity.this,Login.class));
                Toast.makeText(getApplication(), "Logout", Toast.LENGTH_SHORT).show();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked())
                {
                    Toast.makeText(UserActivity.this, "Switch is on", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(UserActivity.this, "Switch is off", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    ///////////////////////////not done //////////////////////////////////////////////////////////////////////////////////
    private void setmealofonTime() {

        Retrofit instance = ApiClint.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        ModelAddMeal modelAddMeal = new ModelAddMeal();





        modelAddMeal.setAdminuniquetoken(getIntent().getStringExtra("uniquetoken"));

        apiInterface.getAllMealData(modelAddMeal).enqueue(new Callback<List<ModelAddMeal>>() {
            @Override
            public void onResponse(Call<List<ModelAddMeal>> call, Response<List<ModelAddMeal>> response) {



            //    userList.addAll(response.body());
                //userAdapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<ModelAddMeal>> call, Throwable t) {

                Log.d("wrong",""+t.getMessage());


            }
        });
    }

    private void setUserBalance() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
        String username = sharedPreferences.getUsername();

        apiInterface.getUserBalance(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double balance = Double.parseDouble(response.body().getUser_balance());

                userbalance.setText(String.format("%.2f", balance));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setUserCost() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
        String username = sharedPreferences.getUsername();

        apiInterface.getUserCost(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double num = Double.parseDouble(response.body().getUser_cost());

                mycost.setText(String.format("%.2f", num));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });


    }

    private void setmesstotalBalance() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");



        apiInterface.messtotalBalance(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double num = Double.parseDouble(response.body().getMess_total_balance());

                messtotalbalance.setText(String.format("%.2f", num));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });


    }

    private void seteachuserdeposit() {


        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
        String username = sharedPreferences.getUsername();

        apiInterface.geteachuserdeposit(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double num = Double.parseDouble(response.body().getUserdeposit());

                eachuserdeposit.setText(String.format("%.2f", num));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });


    }

    private void setmesstotalexpense() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");



        apiInterface.getmesstotalexpense(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double num = Double.parseDouble(response.body().getMessdailyexpense());

                messtotalcost.setText(String.format("%.2f", num));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });


    }

    private void setusertotalmeal() {


        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");
        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
        String username = sharedPreferences.getUsername();


        apiInterface.getusettotalmeal(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                mymeal.setText(response.body().getUsertotalmeal());

                userTotalMeal = Double.parseDouble(response.body().getUsertotalmeal());


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setmealrate() {



        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");


      apiInterface.getmealrate(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
          @Override
          public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
              //mealrate.setText(response.body().getMeal_rate());

          meal_rate = Double.parseDouble(response.body().getMeal_rate());

              mealrate.setText(String.format("%.2f", meal_rate));


          }

          @Override
          public void onFailure(Call<ModelAddMeal> call, Throwable t) {

          }
      });
    }


    private void settotalmeal() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        String adminuniquetoken = getIntent().getStringExtra("uniquetoken");


        apiInterface.gettotalmeal(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                totalmeal.setText(response.body().getTotalmeal());

                MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
                sharedPreferences.setTotalmeal(response.body().getTotalmeal());

                //  Toast.makeText(MainActivity.this, ""+response.body().getTotalmeal(), Toast.LENGTH_SHORT).show();

                // username.setText(sharedPreferences.getTotalmeal());
            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

}
