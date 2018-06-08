package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Step;
import com.example.a77011_40_05.afparestaurant.utils.App;

public class MealHolder extends RecyclerView.ViewHolder {

    private CardView cvMealsBody;
    private TextView txtMeal;
    private Meal meal;

    public MealHolder(View view) {
        super(view);
        cvMealsBody = view.findViewById(R.id.cvMealsBody);
        txtMeal = view.findViewById(R.id.txtMeal);
    }

    public void setMeal(Meal meal, Activity activity){
        this.meal = meal;

        txtMeal.setText(meal.getName());

        cvMealsBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMeal(activity);
            }
        });
    }

    public void selectMeal(Activity activity){
        Toast.makeText(activity,"SELECT: "+meal.getName(),Toast.LENGTH_SHORT).show();

    }
}