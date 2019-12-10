package com.example.smartmeal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    TextView textView;

    TextView mymeal,mycost,mydeposit,mybalance,messbalance,messtotalcost,messmealrate,messtotalmeal,membercount;

    ApiInterface apiInterface;
    MySharedPreferences sharedPreferences;
    double meal_rate= 0.0;
    double userTotalMeal = 0.0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        messbalance = view.findViewById(R.id.m_tv_mess_total_balance);
        messtotalcost = view.findViewById(R.id.m_tv_mess_total_cost);
        messmealrate = view.findViewById(R.id.m_tv_meal_rate);
        messtotalmeal = view.findViewById(R.id.m_tv_mess_totalmeal);
        membercount = view.findViewById(R.id.m_member_count);


        mymeal = view.findViewById(R.id.m_tv_my_total_meal);
        mycost = view.findViewById(R.id.m_tv_my_cost);
        mydeposit = view.findViewById(R.id.m_tv_each_member_deposit);
        mybalance = view.findViewById(R.id.m_tv_user_balance);


        setmesstotalBalance();
        setmesstotalexpense();
        setmealrate();
        settotalmeal();

        setusertotalmeal();
        seteachuserdeposit();
        setUserCost();
        setUserBalance();

        setmembercount();



        return view;
    }

    private void setmembercount() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());
        String adminuniquetoken = sharedPreferences.getUniqueId();

        apiInterface.getmembercount(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
               membercount.setText(response.body().getTotalmember());


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setUserBalance() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();
        String username = sharedPreferences.getData();

        apiInterface.getUserBalance(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {

                double balance = Double.parseDouble(response.body().getUser_balance());

                mybalance.setText(String.format("%.2f", balance));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setUserCost() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();
        String username = sharedPreferences.getData();

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

    private void settotalmeal() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();

        apiInterface.gettotalmeal(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                if (response.body().getTotalmeal().equals("")){
                    messtotalmeal.setText(" 0.00");
                }
                else
                {
                    messtotalmeal.setText(response.body().getTotalmeal());
                }



            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });
    }

    private void setmealrate() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();

        apiInterface.getmealrate(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //messmealrate.setText(response.body().getMeal_rate());

                meal_rate = Double.parseDouble(response.body().getMeal_rate());

               messmealrate.setText(String.format("%.2f", meal_rate));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setmesstotalexpense() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();


        apiInterface.getmesstotalexpense(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                //double num = Double.parseDouble(response.body().getMessdailyexpense());

                //messtotalcost.setText(String.format("%.2f", num));

                if (response.body().getMessdailyexpense().equals("")){
                    messtotalcost.setText(" 0.00");
                }
                else
                {
                    messtotalcost.setText(response.body().getMessdailyexpense());
                }



            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void setmesstotalBalance() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);

        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());

        String adminuniquetoken = sharedPreferences.getUniqueId();



        apiInterface.messtotalBalance(adminuniquetoken).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {
                //mealrate.setText(response.body().getMeal_rate());

                double num = Double.parseDouble(response.body().getMess_total_balance());

                messbalance.setText(String.format("%.2f", num));


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });

    }

    private void seteachuserdeposit() {

        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);



        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());
        String username = sharedPreferences.getData();
        String adminuniquetoken = sharedPreferences.getUniqueId();

        apiInterface.geteachuserdeposit(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {




                if (response.body().getUserdeposit().equals("")){
                    mydeposit.setText("0.00");
                }else {


                   mydeposit.setText(response.body().getUserdeposit());
                }


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });
    }


    private void setusertotalmeal() {
        final Retrofit instance = ApiClint.instance();

        apiInterface = instance.create(ApiInterface.class);


        MySharedPreferences sharedPreferences = MySharedPreferences.getPreferences(getContext());
        String username = sharedPreferences.getData();
        String adminuniquetoken = sharedPreferences.getUniqueId();


        apiInterface.getusettotalmeal(adminuniquetoken,username).enqueue(new Callback<ModelAddMeal>() {
            @Override
            public void onResponse(Call<ModelAddMeal> call, Response<ModelAddMeal> response) {

           //    userTotalMeal = Double.parseDouble(response.body().getUsertotalmeal());

          //     mymeal.setText(""+userTotalMeal);

                if (response.body().getUsertotalmeal().equals("")){
                    mymeal.setText("0.00");
                }else
                    {

                    mymeal.setText(response.body().getUsertotalmeal());
                }


            }

            @Override
            public void onFailure(Call<ModelAddMeal> call, Throwable t) {

            }
        });
    }
}