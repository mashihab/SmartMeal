package com.example.smartmeal.retrofit;

import com.example.smartmeal.model.ModelAddMeal;
import com.example.smartmeal.model.ModelDailyCost;
import com.example.smartmeal.model.ModelInsertUserDetails;
import com.example.smartmeal.model.ModelInsertadminDetails;
import com.example.smartmeal.model.ModelUsers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
   // @GET("get_all_meal.php")
   // Call<List<ModelAddMeal>> getAllMealData();


 @GET("v2/meal_rate.php")
 Call<List<ModelUsers>> getmealrate();

   @POST("get_all_meal.php")
    Call<List<ModelAddMeal>> getAllMealData(@Body ModelAddMeal modelAddMeal);


 @POST("get_meal_ofon_time.php")
 Call<List<ModelAddMeal>> getmealofonTime(@Body ModelAddMeal modelAddMeal);



    @POST("insert_managerdetails.php")
    Call<ModelInsertadminDetails> inputmanagerData(@Body ModelInsertadminDetails modelInsertadminDetails);


    //insert daily cost
   @POST("insert_daily_cost.php")
   Call<ModelDailyCost> insertdailycost(@Body ModelDailyCost modelDailyCost);

   //insert member deposit
   @POST("insert_member_deposit.php")
   Call<ModelDailyCost> insertmemberdeposit(@Body ModelDailyCost modelDailyCost);

    @POST("insert_userdetails.php")
    Call<ModelInsertUserDetails> inputuserData(@Body ModelInsertUserDetails modelInsertUserDetails);


    @POST("updateStartEndTime.php")
    Call<ModelInsertadminDetails> updateTime(@Body ModelInsertadminDetails modelInsertadminDetails);


    //get total member count
    @GET("v2/get_total_member.php")
    Call<ModelAddMeal> getmembercount(@Query("adminuniquetoken") String Adminuniquetoken);



    //get total meal
    @GET("v2/get_total_meal.php")
    Call<ModelAddMeal> gettotalmeal(@Query("adminuniquetoken") String Adminuniquetoken);

    //get user total meal
    @GET("v2/each_user_total_meal.php")
    Call<ModelAddMeal> getusettotalmeal(@Query("adminuniquetoken") String Adminuniquetoken, @Query("username") String Username);

    //get each member deposit
    @GET("v2/each_member_deposit.php")
    Call<ModelAddMeal> geteachuserdeposit(@Query("adminuniquetoken") String Adminuniquetoken, @Query("username") String Username);


    //get user cost
    @GET("v2/user_cost.php")
    Call<ModelAddMeal> getUserCost(@Query("adminuniquetoken") String Adminuniquetoken, @Query("username") String Username);

    //get user Balance
    @GET("v2/user_balance.php")
    Call<ModelAddMeal> getUserBalance(@Query("adminuniquetoken") String Adminuniquetoken, @Query("username") String Username);



    //mess total balance
    @GET("v2/mess_total_balance.php")
    Call<ModelAddMeal> messtotalBalance(@Query("adminuniquetoken") String Adminuniquetoken);


    //mess total expense
    @GET("v2/mess_total_expense.php")
    Call<ModelAddMeal> getmesstotalexpense(@Query("adminuniquetoken") String Adminuniquetoken);






 //get meal rate
    @GET("v2/meal_rate.php")
    Call<ModelAddMeal> getmealrate(@Query("adminuniquetoken") String Adminuniquetoken);




 @GET("login.php")
    Call<ModelUsers> userLogin(@Query("username") String Username, @Query("password") String Password);


 //get meal off on time by sending uniqueToken
    @GET("get_meal_offon_time.php")
    Call<ModelInsertadminDetails> getoffonTime(@Query("muniquetoken") String Muniquetoken);


    @GET("loginMember.php")
    Call<ModelUsers> loginMember(@Query("username") String Username, @Query("password") String Password);

    //update password
    @GET("change_password.php")
    Call<ModelUsers> changepassword(@Query("username") String Username, @Query("password") String Password);


}
