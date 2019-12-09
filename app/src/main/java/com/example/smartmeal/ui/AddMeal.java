package com.example.smartmeal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.adapter.Adapter;
import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.model.ModelInsertadminDetails;
import com.example.smartmeal.model.ModelUsers;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.loginsignup.Login;
import com.example.smartmeal.ui.loginsignup.SignUp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddMeal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Adapter adapter;
    ArrayList<ModelAddMeal> addMeals;
    RecyclerView recyclerView;
    TextView selectDate,addmeal;
    TextView insertallmeal;
    ApiInterface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        selectDate = findViewById(R.id.tv_select_date);

        addMeals = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_add_mealId);
        adapter = new Adapter(addMeals,AddMeal.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        addmeal = findViewById(R.id.tv_addmembermeal);
        insertallmeal = findViewById(R.id.tv_insert_today);

        //Show username




        String username = getIntent().getStringExtra("username");

       Retrofit instance = ApiClint.instance();
     ApiInterface apiInterface = instance.create(ApiInterface.class);

     ModelAddMeal modelAddMeal = new ModelAddMeal();

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());


     modelAddMeal.setAdminuniquetoken(sharedPreferences.getUniqueId());

       apiInterface.getAllMealData(modelAddMeal).enqueue(new Callback<List<ModelAddMeal>>() {
           @Override
           public void onResponse(Call<List<ModelAddMeal>> call, Response<List<ModelAddMeal>> response) {


               addMeals.addAll(response.body());
               adapter.notifyDataSetChanged();


           }

           @Override
           public void onFailure(Call<List<ModelAddMeal>> call, Throwable t) {

               Log.d("wrong",""+t.getMessage());

              
           }
       });


    }



    public void selectDateonClick(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int monthCorrect = month+1;
        String date = ""+dayOfMonth+"-" +monthCorrect+ "-" +year;

        selectDate.setText(date);
    }
}
