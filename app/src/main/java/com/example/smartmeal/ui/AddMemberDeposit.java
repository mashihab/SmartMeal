package com.example.smartmeal.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddMemberDeposit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button submit,selectdate;
    EditText depositet;
    ApiInterface apiInterface;
    String username;

    DatePickerDialog datePickerDialog;

    //for show username from sql database
    Spinner spinner;
    ArrayList<ModelAddMeal> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_deposit);

        depositet = findViewById(R.id.et_user_deposit);
        submit = findViewById(R.id.bt_user_deposit_submit);
        selectdate = findViewById(R.id.bt_datePickerId_deposit);
        spinner = findViewById(R.id.sp_username_deposit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getApplicationContext());
                String uniquetoken = sharedPreferences.getUniqueId();
                int deposit = Integer.parseInt(depositet.getText().toString());
                String date = selectdate.getText().toString();



                ModelDailyCost modelDailyCost = new ModelDailyCost();
                modelDailyCost.setUsername(username);
                modelDailyCost.setUniquetoken(uniquetoken);
                modelDailyCost.setDeposit(deposit);
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
                Toast.makeText(AddMemberDeposit.this, ""+userList.get(position).getUsername(), Toast.LENGTH_SHORT).show();

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

        apiInterface.insertmemberdeposit(modelDailyCost).enqueue(new Callback<ModelDailyCost>() {
            @Override
            public void onResponse(Call<ModelDailyCost> call, Response<ModelDailyCost> response) {
//                Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Deposit Taka is Added", Toast.LENGTH_SHORT).show();
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

        selectdate.setText(date);
    }
}
