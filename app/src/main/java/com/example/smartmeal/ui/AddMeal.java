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
import com.example.smartmeal.model.ModelInsertUserDetails;
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
    TextView selectDate, addmeal;
    TextView insertallmeal;
    ApiInterface apiInterface;

   // final ArrayList<ModelAddMeal> allData;

    private static final String TAG = "AddMeal";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        selectDate = findViewById(R.id.tv_select_date);

        addMeals = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_add_mealId);
        adapter = new Adapter(addMeals, AddMeal.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        addmeal = findViewById(R.id.tv_addmembermeal);
        insertallmeal = findViewById(R.id.tv_insert_today);

        //Show username


        insertallmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // final ArrayList<ModelAddMeal> allData = adapter.getAllData();
                addMeals = adapter.getAllData();
               // Log.d(TAG, "onClick: " + addMeals.get(0).getBreakfast() +"-  "+  addMeals.get(1).getBreakfast());

                int i=0;


              /*  ModelAddMeal modelAddMeal = new ModelAddMeal();
                modelAddMeal.setUsername("shihab");
                modelAddMeal.setUniquetoken("235346");
                modelAddMeal.setBreakfast("2");
                modelAddMeal.setLunch("3");
                modelAddMeal.setDinner("10");
                modelAddMeal.setDate("20-10-2029");*/






                for(i=0;i<addMeals.size();i++){

                 //   Toast.makeText(AddMeal.this, addMeals.get(i).getUsername()+"-"+addMeals.get(i).getBreakfast()+"-"+addMeals.get(i).getLunch()+"-"+addMeals.get(i).getDinner()+"-"+addMeals.get(i).getCheckMeal(), Toast.LENGTH_SHORT).show();



                    MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplication());
                    String uniquetoken = sharedPreferences.getUniqueId();

                    String date = selectDate.getText().toString();

                    ModelAddMeal modelAddMeal = new ModelAddMeal();
                    modelAddMeal.setUsername(addMeals.get(i).getUsername());
                    modelAddMeal.setUniquetoken(uniquetoken);
                    modelAddMeal.setBreakfast(addMeals.get(i).getBreakfast());
                    modelAddMeal.setLunch(addMeals.get(i).getLunch());
                    modelAddMeal.setDinner(addMeals.get(i).getDinner());
                    modelAddMeal.setDate(date);

                    sendDatauserr(modelAddMeal);

                 /*   final Retrofit instance = ApiClint.instance();

                    apiInterface = instance.create(ApiInterface.class);

                    apiInterface.addDailyMeal(addMeals.get(i).getUsername(),uniquetoken, addMeals.get(i).getBreakfast(),addMeals.get(i).getLunch(),addMeals.get(i).getDinner(), String.valueOf(selectDate)).enqueue(new Callback<ModelAddMeal>() {
                        @Override
                        public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {

                            if (response.body().getResponse().equals("ok"))
                            {
                                Toast.makeText(AddMeal.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();

                            }
                            else if (response.body().getResponse().equals("failed")){
                                Toast.makeText(getApplicationContext(), "Username or password is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelAddMeal> call, Throwable t) {

                        }
                    });*/







              }





              // callMethod();



            }
        });

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

                Log.d("wrong", "" + t.getMessage());


            }
        });



    }

    private void sendDatauserr(ModelAddMeal modelAddMeal) {

        final Retrofit instance = ApiClint.instance();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.addDailyMeal(modelAddMeal).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                Toast.makeText(getApplicationContext(), "Meal Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {
                Log.d("Throwable",""+t.getMessage());
            }
        });



    }


    public void selectDateonClick(View view) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int monthCorrect = month + 1;
        String date = "" + dayOfMonth + "-" + monthCorrect + "-" + year;

        selectDate.setText(date);
    }


    int i=0;
  //  separated = currentString.split("\n");



    //String date = selectDate.getText().toString();

  /*  void callMethod() {
       // seperated2 = separated[i].split(":");

        Call<ModelAddMeal> call = apiInterface.addDailyMeal(addMeals.get(i).getUsername(),uniquetoken, addMeals.get(i).getBreakfast(),addMeals.get(i).getLunch(),addMeals.get(i).getDinner(), String.valueOf(selectDate));
        call.enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                ModelAddMeal serverResponse2 = response.body();
               // Toast.makeText(getApplicationContext(), serverResponse2 != null ? serverResponse2.getMessage() : null, Toast.LENGTH_SHORT).show();
                i++;
                callMethod();
            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
