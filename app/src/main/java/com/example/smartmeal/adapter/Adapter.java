package com.example.smartmeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartmeal.R;
import com.example.smartmeal.model.ModelAddMeal;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    ArrayList<ModelAddMeal> addMeals;
    Context context;

    CheckBox checkBoxYesNo;
    List<String> breakfast,lunch,dinner;
    String userid;

    public Adapter(ArrayList<ModelAddMeal> addMeals, Context context) {
        this.addMeals = addMeals;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_meal,parent,false);





        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(""+addMeals.get(position).getUsername());

        breakfast = new ArrayList<String>();
        breakfast.add("1");
        breakfast.add(".5");
        breakfast.add("1");
        breakfast.add("1.5");
        breakfast.add("2");
        breakfast.add("2.5");
        breakfast.add("3");
        breakfast.add("3.5");
        breakfast.add("4");
        breakfast.add("4.5");
        breakfast.add("5");


       /* lunch = new ArrayList<String>();
        lunch.add("2");
        lunch.add(".5");
        lunch.add("1");
        lunch.add("1.5");
        lunch.add("2");
        lunch.add("2.5");
        lunch.add("3");
        lunch.add("3.5");
        lunch.add("4");
        lunch.add("4.5");
        lunch.add("5");*/


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, breakfast);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.breakfast.setAdapter(dataAdapter);
        holder.lunch.setAdapter(dataAdapter);
        holder.dinner.setAdapter(dataAdapter);


        String yes = "Yes";
        if (yes.equals(addMeals.get(position).getCheckMeal()))
        {
            checkBoxYesNo.setChecked(true);
        }
        else{
           checkBoxYesNo.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return addMeals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Spinner breakfast,lunch,dinner;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_addmeal);

            checkBoxYesNo = itemView.findViewById(R.id.cb_yesNoId);
            breakfast = itemView.findViewById(R.id.sp_breakfastId);
            lunch = itemView.findViewById(R.id.sp_lanchId);
            dinner = itemView.findViewById(R.id.sp_dinnerId);
        }
    }
}
