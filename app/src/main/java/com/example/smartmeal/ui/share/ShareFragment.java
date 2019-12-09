package com.example.smartmeal.ui.share;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelInsertUserDetails;
import com.example.smartmeal.model.ModelInsertadminDetails;
import com.example.smartmeal.retrofit.ApiClint;
import com.example.smartmeal.retrofit.ApiInterface;
import com.example.smartmeal.sharedpreference.MySharedPreferences;
import com.example.smartmeal.ui.loginsignup.Login;
import com.example.smartmeal.ui.loginsignup.SignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    ApiInterface apiInterface;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
       // final TextView textView = root.findViewById(R.id.text_share);

       final Button button = root.findViewById(R.id.bt_mealtimeoff_id);
     final EditText   editText1 = root.findViewById(R.id.et_start_time_id);
     final EditText   editText2 = root.findViewById(R.id.et_end_time_id);

        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String startTime = editText1.getText().toString();
                        String endTime = editText2.getText().toString();

                        if(startTime.isEmpty() || endTime.isEmpty()){
                            editText1.setError("Set Start Time");
                            editText2.setError("Set End Time");

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Start time: "+startTime+"  End Time: "+endTime , Toast.LENGTH_SHORT).show();
                            MySharedPreferences sharedPreferences;
                           sharedPreferences = MySharedPreferences.getPreferences(getActivity());


                           String getStarTime = editText1.getText().toString();
                            String getEndTime = editText2.getText().toString();

                            ModelInsertadminDetails modelInsertadminDetails = new ModelInsertadminDetails();
                            modelInsertadminDetails.setUsername(sharedPreferences.getData());
                            modelInsertadminDetails.setStarttime(getStarTime);
                            modelInsertadminDetails.setEndtime(getEndTime);

                            sendDatauser(modelInsertadminDetails);

                        }



                    }
                });


            }
        });
        return root;
    }

    private void sendDatauser(ModelInsertadminDetails modelInsertadminDetails) {

        final Retrofit instance = ApiClint.instance();
        apiInterface = instance.create(ApiInterface.class);

        apiInterface.updateTime(modelInsertadminDetails).enqueue(new Callback<ModelInsertadminDetails>() {
            @Override
            public void onResponse(Call<ModelInsertadminDetails> call, Response<ModelInsertadminDetails> response) {

                Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);



            }

            @Override
            public void onFailure(Call<ModelInsertadminDetails> call, Throwable t) {
                Log.d("Throwable",""+t.getMessage());
            }
        });

    }
}