package com.example.smartmeal;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.AddDailyCost;
import com.example.smartmeal.ui.AddMeal;
import com.example.smartmeal.ui.AddMemberDeposit;
import com.example.smartmeal.ui.UserActivity;
import com.example.smartmeal.ui.loginsignup.Login;
import com.getbase.floatingactionbutton.FloatingActionButton;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView username,email,totalmeal;
    MySharedPreferences sharedPreferences;
    ApiInterface apiInterface;
    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        settotalmeal();





        FloatingActionButton fab1 = findViewById(R.id.fab_action1);
        FloatingActionButton fab2 = findViewById(R.id.fab_action2);
        FloatingActionButton fab3 = findViewById(R.id.fab_action3);
        FloatingActionButton fab4 = findViewById(R.id.fab_action4);




        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeal.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDailyCost.class);
                startActivity(intent);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberDeposit.class);
                startActivity(intent);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTost("Fab 4");
            }
        });





        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headView = navigationView.getHeaderView(0);



        username = headView.findViewById(R.id.userName_id);
        email = headView.findViewById(R.id.tv_head_email_id);



        sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());

      username.setText(sharedPreferences.getData().toString());
      email.setText(sharedPreferences.getEmail().toString());

        //Toast.makeText(this, sharedPreferences.getUniqueId(), Toast.LENGTH_SHORT).show();


        sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());

        if(sharedPreferences.getData().equals("none"))
        {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        else {

            Toast.makeText(this, "Congrats "+sharedPreferences.getData() +", You are already Logged in", Toast.LENGTH_SHORT).show();

        }



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void settotalmeal() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        apiInterface.gettotalmeal("26355").enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
              // totalmeal.setText(response.body().getTotalmeal());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch (item.getItemId())
        {
            case R.id.action_logout:
                sharedPreferences.setData("none");
                startActivity(new Intent(MainActivity.this,Login.class));
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    public void showTost(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
