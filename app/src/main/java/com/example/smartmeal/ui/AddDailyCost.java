package com.example.smartmeal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.model.ModelDailyCost;
import com.example.smartmeal.model.ModelInsertadminDetails;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.loginsignup.Login;
import com.example.smartmeal.ui.loginsignup.SignUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddDailyCost extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button addBazarList,selectDate,submitButton;
    EditText et_bazerList,addDailyCost;
    DatePickerDialog datePickerDialog;
    String username;
    ApiInterface apiInterface;
    //for show username from sql database
    Spinner spinner;
    ArrayList<ModelAddMeal> userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_cost);

        et_bazerList = findViewById(R.id.et_bazerList_id);
        addDailyCost = findViewById(R.id.add_daily_cost_ammount);
        selectDate = findViewById(R.id.bt_datePickerId);
        addBazarList = findViewById(R.id.add_bazar_list_id);
        spinner = findViewById(R.id.sp_userlist);
        submitButton = findViewById(R.id.add_daily_cost_submit);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
                String uniquetoken = sharedPreferences.getUniqueId();
                int dailycost = Integer.parseInt(addDailyCost.getText().toString());
                String bazar_list = et_bazerList.getText().toString();
                String date = selectDate.getText().toString();



                ModelDailyCost modelDailyCost = new ModelDailyCost();
                modelDailyCost.setUsername(username);
                modelDailyCost.setUniquetoken(uniquetoken);
                modelDailyCost.setDaily_expense(dailycost);
                modelDailyCost.setBazar_list(bazar_list);
                modelDailyCost.setDate(date);



                sendData(modelDailyCost);




            }
        });


        userList = new ArrayList<>();
        spinnerusername();
        spinner.setAdapter(userAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddDailyCost.this, ""+userList.get(position).getUsername(), Toast.LENGTH_SHORT).show();

                username = userList.get(position).getUsername();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void sendData(ModelDailyCost modelDailyCost) {


        final Retrofit instance = ApiClint.instance();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.insertdailycost(modelDailyCost).enqueue(new Callback<ModelDailyCost>() {
            @Override
            public void onResponse(Call<ModelDailyCost> call, Response<ModelDailyCost> response) {
//                Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Daily cost Added", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(), Login.class);
               // startActivity(intent);
            }

            @Override
            public void onFailure(Call<ModelDailyCost> call, Throwable t) {
                Log.d("Throwable",""+t.getMessage());
            }
        });


    }

    private void spinnerusername() {

        Retrofit instance = ApiClint.instance();
        ApiInterface apiInterface = instance.create(ApiInterface.class);

        ModelAddMeal modelAddMeal = new ModelAddMeal();


        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());


        modelAddMeal.setAdminuniquetoken(sharedPreferences.getUniqueId());

        apiInterface.getAllMealData(modelAddMeal).enqueue(new Callback<List<ModelAddMeal>>() {
            @Override
            public void onResponse(Call<List<ModelAddMeal>> call, Response<List<ModelAddMeal>> response) {


       /*         spinnerArray.addAll(response.body());
                adapter.notifyDataSetChanged();*/

                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<ModelAddMeal>> call, Throwable t) {

                Log.d("wrong",""+t.getMessage());


            }
        });

    }

    public void addBazerList(View view) {

        String ButtonText = addBazarList.getText().toString();

        if (ButtonText.equals("Add Bazar List(optional)")){
            et_bazerList.setVisibility(View.VISIBLE);
            addBazarList.setText("Add Bazar List(optional) ");
        }else
        {
            et_bazerList.setVisibility(View.GONE);
            addBazarList.setText("Add Bazar List(optional)");
        }

        //et_bazerList.setVisibility(View.VISIBLE);

    }

    public void selectDateButtionClick(View view) {

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


    //for spinner adapter
   private BaseAdapter userAdapter = new BaseAdapter() {
       @Override
       public int getCount() {
           return userList.size();
       }

       @Override
       public Object getItem(int position) {
           return userList.get(position);
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {

           CategoryHolder holder = null;
           View categoryView = convertView;
           if (categoryView == null){

               categoryView = getLayoutInflater().inflate(R.layout.item_username_list_spinner,parent,false);

               holder = new CategoryHolder();
               holder.username = categoryView.findViewById(R.id.tv_username_list);
               categoryView.setTag(holder);

           }else {

               holder = (CategoryHolder) categoryView.getTag();

           }

           holder.username.setText(userList.get(position).getUsername());

           return categoryView;
       }

       class CategoryHolder{
           private TextView username;
       }

   };






}
