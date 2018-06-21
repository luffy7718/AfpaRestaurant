package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.MealAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

public class MealHolder extends RecyclerView.ViewHolder {

    private boolean v2 = false;
    private CardView cvMealsBody;
    private TextView txtMeal;
    private TextView txtQuantity;
    private ImageButton addQuantity;
    private ImageButton removeQuantity;
    private NumberPicker npQuantity;
    private Meal meal;
    MealAdapter parent;
    private int min = 0;
    private int max = 20;

    public MealHolder(View view) {
        super(view);
        cvMealsBody = view.findViewById(R.id.cvMealsBody);
        txtMeal = view.findViewById(R.id.txtMeal);
        if (v2) {
            npQuantity = view.findViewById(R.id.npQuantity);
        } else {
            txtQuantity = view.findViewById(R.id.txtQuantity);
            addQuantity = view.findViewById(R.id.addQuantity);
            removeQuantity = view.findViewById(R.id.removeQuantity);
        }
    }

    public void setMeal(Meal meal, MealAdapter parent) {
        this.meal = meal;
        this.parent = parent;
        txtMeal.setText(meal.getName());

        if (!v2) {
            txtQuantity.setText("" + meal.getQuantity());

            addQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addQuantity();
                }
            });

            removeQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeQuantity();
                }
            });
        } else {
            npQuantity.setMinValue(min);
            npQuantity.setMaxValue(max);
            npQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    meal.setQuantity(newVal);

                }
            });

        }

    }

    public void selectMeal(Activity activity) {
        Toast.makeText(activity, "SELECT: " + meal.getName(), Toast.LENGTH_SHORT).show();

    }

    public void addQuantity() {
        meal.addQuantity();
        txtQuantity.setText("" + meal.getQuantity());
    }

    public void removeQuantity() {
        meal.removeQuantity();
        txtQuantity.setText("" + meal.getQuantity());
    }


}