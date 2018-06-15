package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;

public class OrderHolder extends RecyclerView.ViewHolder {


    private TextView txtName;
    private ImageButton btnRemove;
    private Meal meal;

    public OrderHolder(View view) {
        super(view);
        txtName = view.findViewById(R.id.txtName);
        btnRemove = view.findViewById(R.id.btnRemove);
    }

    public void setMeal(Meal meal, Activity activity, OrderAdapter parent) {
        this.meal = meal;

        txtName.setText(meal.getName());

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.remove(getAdapterPosition());
            }
        });

    }
}